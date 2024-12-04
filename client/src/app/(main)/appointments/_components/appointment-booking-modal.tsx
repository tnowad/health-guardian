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
import { useInfiniteQuery, useQuery } from "@tanstack/react-query";
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

interface AppointmentBookingModalProps {
  isOpen: boolean;
  onClose: () => void;
}

export default function AppointmentBookingModal({
  isOpen,
  onClose,
}: AppointmentBookingModalProps) {
  const listUserMedicalStaffsInfinityQuery = useInfiniteQuery(
    createListUserMedicalStaffsInfinityQueryOptions({}),
  );

  const createAppointmentMutation = useCreateAppointmentMutation();
  const createAppointmentForm = useForm<CreateAppointmentBodySchema>({
    resolver: zodResolver(createAppointmentBodySchema),
    defaultValues: {
      patientId: "",
      status: "SCHEDULED",
      doctorId: "",
      reasonForVisit: "",
      appointmentDate: "",
    },
  });

  const [doctorSearchQuery, setDoctorSearchQuery] = useState("");

  const lastUserMedicalStaffsCommandItemObserver =
    useRef<IntersectionObserver>(null);
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

  const [doctor, setDoctor] = useState("");
  const [date, setDate] = useState("");
  const [reason, setReason] = useState("");

  const handleSubmit = createAppointmentForm.handleSubmit((data) =>
    createAppointmentMutation.mutate(data),
  );

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="sm:max-w-[425px]">
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
                    <FormItem className="flex flex-col">
                      <FormLabel>Doctor</FormLabel>
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
                                  {
                                    doctors.find(
                                      (doctor) => doctor.id === field.value,
                                    )?.userId
                                  }
                                </span>
                              ) : (
                                "Select doctor"
                              )}
                              <ChevronsUpDown className="ml-2 h-4 w-4 shrink-0 opacity-50" />
                            </Button>
                          </FormControl>
                        </PopoverTrigger>
                        <PopoverContent className="p-0">
                          <Command shouldFilter={false}>
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
                                    {doctor.userId}
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
                <Input
                  id="date"
                  type="datetime-local"
                  value={date}
                  onChange={(e) => setDate(e.target.value)}
                  className="col-span-3"
                  required
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <label htmlFor="reason" className="text-right">
                  Reason
                </label>
                <Textarea
                  id="reason"
                  value={reason}
                  onChange={(e) => setReason(e.target.value)}
                  className="col-span-3"
                  required
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
