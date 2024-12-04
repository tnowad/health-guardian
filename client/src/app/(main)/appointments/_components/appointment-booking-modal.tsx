import { useCallback, useMemo, useRef, useState } from "react";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogFooter,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import {
  useInfiniteQuery,
  useQuery,
  useSuspenseQuery,
} from "@tanstack/react-query";
import {
  createListUserMedicalStaffsInfinityQueryOptions,
  createListUserMedicalStaffsQueryOptions,
} from "@/lib/apis/list-user-medical-staffs.api";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import {
  createAppointmentApi,
  createAppointmentBodySchema,
  CreateAppointmentBodySchema,
  useCreateAppointmentMutation,
} from "@/lib/apis/create-appointment.api";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { cn } from "@/lib/utils";
import { ChevronsUpDown } from "lucide-react";
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from "@/components/ui/command";
import { useToast } from "@/hooks/use-toast";
import { createListUserStaffsQueryOptions } from "@/lib/apis/list-user-staffs.api";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";

interface AppointmentBookingModalProps {
  isOpen: boolean;
  onClose: () => void;
}

export default function AppointmentBookingModal({
  isOpen,
  onClose,
}: AppointmentBookingModalProps) {
  const { toast } = useToast();
  const getCurrentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions(),
  );

  const listUserMedicalStaffsInfinityQuery = useInfiniteQuery(
    createListUserMedicalStaffsInfinityQueryOptions({}),
  );

  const createAppointmentMutation = useCreateAppointmentMutation();
  const createAppointmentForm = useForm<CreateAppointmentBodySchema>({
    resolver: zodResolver(createAppointmentBodySchema),
    defaultValues: {
      patientId: getCurrentUserInformationQuery.data?.patient.id ?? "",
      status: "SCHEDULED",
      doctorId: "",
      reasonForVisit: "",
      appointmentDate: "",
    },
  });

  const [doctorSearchQuery, setDoctorSearchQuery] = useState("");

  const lastUserMedicalStaffsCommandItemObserver =
    useRef<IntersectionObserver | null>(null);
  const lastUserMedicalStaffsCommandItemElementRef = useCallback(
    (node: HTMLDivElement) => {
      if (listUserMedicalStaffsInfinityQuery.isLoading) return;
      if (lastUserMedicalStaffsCommandItemObserver.current)
        lastUserMedicalStaffsCommandItemObserver.current.disconnect();
      lastUserMedicalStaffsCommandItemObserver.current =
        new IntersectionObserver((entries) => {
          if (
            entries[0].isIntersecting &&
            listUserMedicalStaffsInfinityQuery.hasNextPage &&
            !listUserMedicalStaffsInfinityQuery.isFetching
          ) {
            listUserMedicalStaffsInfinityQuery.fetchNextPage();
          }
        });
      if (node) lastUserMedicalStaffsCommandItemObserver.current.observe(node);
    },
    [listUserMedicalStaffsInfinityQuery],
  );

  const doctors = useMemo(() => {
    return (
      listUserMedicalStaffsInfinityQuery.data?.pages.flatMap(
        (page) => page.content,
      ) ?? []
    );
  }, [listUserMedicalStaffsInfinityQuery.data]);
  const listUserStaffsQuery = useQuery(
    createListUserStaffsQueryOptions({
      ids: doctors.map((doctor) => doctor.userId),
    }),
  );

  const userStaffs = useMemo(() => {
    return listUserStaffsQuery.data?.content ?? [];
  }, [listUserStaffsQuery.data]);

  const handleSubmit = createAppointmentForm.handleSubmit((data) =>
    createAppointmentMutation.mutate(data, {
      onSuccess() {
        toast({
          title: "Appointment created",
          description: "Appointment has been successfully created.",
        });
      },
    }),
  );

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent
        className="sm:max-w-[425px]"
        onWheel={(event) => event.stopPropagation()}
      >
        <DialogHeader>
          <DialogTitle>Book Appointment</DialogTitle>
        </DialogHeader>
        <Form {...createAppointmentForm}>
          <form onSubmit={handleSubmit}>
            <div className="grid gap-4 py-4">
              <div className="grid grid-cols-4 items-center gap-4">
                <label htmlFor="doctor" className="text-right">
                  Doctor
                </label>

                <FormField
                  control={createAppointmentForm.control}
                  name="doctorId"
                  render={({ field }) => (
                    <FormItem className="flex flex-col col-span-3">
                      <Popover>
                        <PopoverTrigger asChild>
                          <FormControl>
                            <Button
                              variant="outline"
                              role="combobox"
                              className={cn(
                                "justify-between",
                                !field.value && "text-muted-foreground",
                              )}
                            >
                              {field.value ? (
                                <span className="space-x-1">
                                  {userStaffs.find(
                                    (userStaff) =>
                                      userStaff.userId === field.value,
                                  )?.firstName || "Loading..."}
                                </span>
                              ) : (
                                "Select doctor"
                              )}
                              <ChevronsUpDown className="ml-2 h-4 w-4 shrink-0 opacity-50" />
                            </Button>
                          </FormControl>
                        </PopoverTrigger>
                        <PopoverContent className="p-0">
                          <Command
                            shouldFilter={false}
                            onWheel={(event) => event.stopPropagation()}
                          >
                            <CommandInput
                              placeholder="Search doctors..."
                              value={doctorSearchQuery}
                              onValueChange={setDoctorSearchQuery}
                            />
                            <CommandList>
                              <CommandEmpty>No doctors found.</CommandEmpty>
                              <CommandGroup>
                                {doctors.map((doctor) => (
                                  <CommandItem
                                    key={doctor.id}
                                    value={doctor.id}
                                    onSelect={() => {
                                      field.onChange(doctor.id);
                                      createAppointmentForm.setValue(
                                        "doctorId",
                                        doctor.id,
                                      );
                                    }}
                                  >
                                    {userStaffs.find(
                                      (userStaff) =>
                                        userStaff.userId === doctor.userId,
                                    )?.firstName || "Loading..."}
                                  </CommandItem>
                                ))}
                                {listUserMedicalStaffsInfinityQuery.hasNextPage && (
                                  <CommandItem
                                    ref={
                                      lastUserMedicalStaffsCommandItemElementRef
                                    }
                                  >
                                    Loading doctors...
                                  </CommandItem>
                                )}
                              </CommandGroup>
                            </CommandList>
                          </Command>
                        </PopoverContent>
                      </Popover>
                      <FormDescription>
                        This user will have the selected doctor.
                      </FormDescription>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <label htmlFor="date" className="text-right">
                  Date
                </label>

                <FormField
                  control={createAppointmentForm.control}
                  name="appointmentDate"
                  render={({ field }) => (
                    <FormItem className="col-span-3">
                      <Input
                        id="date"
                        type="datetime-local"
                        {...field}
                        required
                      />
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <label htmlFor="reason" className="text-right">
                  Reason
                </label>

                <FormField
                  control={createAppointmentForm.control}
                  name="reasonForVisit"
                  render={({ field }) => (
                    <FormItem className="col-span-3">
                      <Textarea id="reason" {...field} required />
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>
            </div>
            <DialogFooter>
              <Button type="button" variant="outline" onClick={onClose}>
                Cancel
              </Button>
              <Button type="submit">Confirm</Button>
            </DialogFooter>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  );
}
