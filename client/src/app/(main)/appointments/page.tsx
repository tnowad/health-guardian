"use client";

import { useState } from "react";
import { format } from "date-fns";
import { CalendarIcon } from "lucide-react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Calendar } from "@/components/ui/calendar";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { Badge } from "@/components/ui/badge";
import { z } from "zod";
import {
  appointmentSchema,
  AppointmentSchema,
} from "@/lib/schemas/(appointment)/appointment.schema";
import { useQuery, useSuspenseQuery } from "@tanstack/react-query";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
import { createListAppointmentsQueryOptions } from "@/lib/apis/(appointment)/list-appointment.api";

export default function AppointmentsScreen() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingAppointment, setEditingAppointment] =
    useState<AppointmentSchema | null>(null);
  const [newAppointment, setNewAppointment] = useState<
    Partial<AppointmentSchema>
  >({
    appointmentDate: new Date().toISOString(),
    reason: "",
    address: "",
    status: "SCHEDULED",
    notes: "",
    userId: "",
  });

  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions()
  );

  const listAppointmentsQuery = useQuery(
    createListAppointmentsQueryOptions({
      userId: currentUserInformationQuery.data?.userId,
    })
  );

  const appointments = listAppointmentsQuery.data?.content ?? [];

  const handleEditAppointment = (appointment: AppointmentSchema) => {
    setEditingAppointment(appointment);
    setNewAppointment(appointment);
    setIsModalOpen(true);
  };

  const handleAddAppointment = () => {
    setEditingAppointment(null);
    setNewAppointment({
      appointmentDate: new Date().toISOString(),
      reason: "",
      address: "",
      status: "SCHEDULED",
      notes: "",
      userId: "",
    });
    setIsModalOpen(true);
  };

  const handleRemoveAppointment = (appointmentId: string) => {
    const updatedAppointments = appointments.filter(
      (app) => app.id !== appointmentId
    );
    setAppointments(updatedAppointments);
  };

  const handleSaveAppointment = () => {
    try {
      const validatedAppointment = appointmentSchema.parse(newAppointment);
      if (editingAppointment) {
        setAppointments(
          appointments.map((app) =>
            app.id === editingAppointment.id ? validatedAppointment : app
          )
        );
      } else {
        setAppointments([
          ...appointments,
          { ...validatedAppointment, id: crypto.randomUUID() },
        ]);
      }
      setIsModalOpen(false);
    } catch (error) {
      console.error("Validation error:", error);
    }
  };

  const getStatusColor = (status: AppointmentSchema["status"]) => {
    switch (status) {
      case "SCHEDULED":
        return "bg-blue-100 text-blue-800";
      case "COMPLETED":
        return "bg-green-100 text-green-800";
      case "CANCELED":
        return "bg-red-100 text-red-800";
      default:
        return "bg-gray-100 text-gray-800";
    }
  };

  return (
    <div className="container mx-auto p-4">
      <Card>
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-2xl font-bold">Appointments</CardTitle>
          <Button onClick={handleAddAppointment}>Add Appointment</Button>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Date</TableHead>
                <TableHead>Reason</TableHead>
                <TableHead>Address</TableHead>
                <TableHead>Status</TableHead>
                <TableHead>Notes</TableHead>
                <TableHead>Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {appointments.map((appointment) => (
                <TableRow key={appointment.id}>
                  <TableCell>
                    {format(new Date(appointment.appointmentDate), "PPP p")}
                  </TableCell>
                  <TableCell>{appointment.reason}</TableCell>
                  <TableCell>{appointment.address}</TableCell>
                  <TableCell>
                    <Badge className={getStatusColor(appointment.status)}>
                      {appointment.status}
                    </Badge>
                  </TableCell>
                  <TableCell>{appointment.notes}</TableCell>
                  <TableCell className="flex space-x-2">
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={() => handleEditAppointment(appointment)}
                    >
                      Edit
                    </Button>
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={() => handleRemoveAppointment(appointment.id)}
                    >
                      Remove
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>

          <Dialog open={isModalOpen} onOpenChange={setIsModalOpen}>
            <DialogContent className="sm:max-w-[425px]">
              <DialogHeader>
                <DialogTitle>
                  {editingAppointment ? "Edit Appointment" : "Add Appointment"}
                </DialogTitle>
              </DialogHeader>
              <div className="grid gap-4 py-4">
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label className="text-right">Date</Label>
                  <Popover>
                    <PopoverTrigger asChild>
                      <Button
                        variant={"outline"}
                        className={`col-span-3 justify-start text-left font-normal ${
                          !newAppointment.appointmentDate &&
                          "text-muted-foreground"
                        }`}
                      >
                        <CalendarIcon className="mr-2 h-4 w-4" />
                        {newAppointment.appointmentDate ? (
                          format(
                            new Date(newAppointment.appointmentDate),
                            "PPP"
                          )
                        ) : (
                          <span>Pick a date</span>
                        )}
                      </Button>
                    </PopoverTrigger>
                    <PopoverContent className="w-auto p-0 z-20">
                      <Calendar
                        mode="single"
                        selected={
                          newAppointment.appointmentDate
                            ? new Date(newAppointment.appointmentDate)
                            : undefined
                        }
                        onSelect={(date) =>
                          setNewAppointment({
                            ...newAppointment,
                            appointmentDate:
                              date?.toISOString() || new Date().toISOString(),
                          })
                        }
                        initialFocus
                      />
                    </PopoverContent>
                  </Popover>
                </div>
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="reason" className="text-right">
                    Reason
                  </Label>
                  <Textarea
                    id="reason"
                    value={newAppointment.reason}
                    onChange={(e) =>
                      setNewAppointment({
                        ...newAppointment,
                        reason: e.target.value,
                      })
                    }
                    className="col-span-3"
                  />
                </div>
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="address" className="text-right">
                    Address
                  </Label>
                  <Textarea
                    id="address"
                    value={newAppointment.address}
                    onChange={(e) =>
                      setNewAppointment({
                        ...newAppointment,
                        address: e.target.value,
                      })
                    }
                    className="col-span-3"
                  />
                </div>
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="status" className="text-right">
                    Status
                  </Label>
                  <Select
                    value={newAppointment.status}
                    onValueChange={(value) =>
                      setNewAppointment({
                        ...newAppointment,
                        status: value as AppointmentSchema["status"],
                      })
                    }
                  >
                    <SelectTrigger className="col-span-3">
                      <SelectValue placeholder="Select status" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="SCHEDULED">Scheduled</SelectItem>
                      <SelectItem value="COMPLETED">Completed</SelectItem>
                      <SelectItem value="CANCELED">Canceled</SelectItem>
                    </SelectContent>
                  </Select>
                </div>
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="notes" className="text-right">
                    Notes
                  </Label>
                  <Textarea
                    id="notes"
                    value={newAppointment.notes}
                    onChange={(e) =>
                      setNewAppointment({
                        ...newAppointment,
                        notes: e.target.value,
                      })
                    }
                    className="col-span-3"
                  />
                </div>
              </div>
              <div className="flex justify-end space-x-2">
                <Button variant="outline" onClick={() => setIsModalOpen(false)}>
                  Cancel
                </Button>
                <Button onClick={handleSaveAppointment}>Save</Button>
              </div>
            </DialogContent>
          </Dialog>
        </CardContent>
      </Card>
    </div>
  );
}
