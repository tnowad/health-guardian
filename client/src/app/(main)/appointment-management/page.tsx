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
import { Badge } from "@/components/ui/badge";
import { Calendar } from "@/components/ui/calendar";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";

export interface Appointment {
  id: string;
  patientName: string;
  date: Date;
  doctor: string;
  reason: string;
  status: "Scheduled" | "Completed" | "Cancelled";
}

export const appointmentsData: Appointment[] = [
  {
    id: "1",
    patientName: "John Doe",
    date: new Date(2023, 5, 15, 10, 0),
    doctor: "Dr. Smith",
    reason: "Annual checkup",
    status: "Scheduled",
  },
  {
    id: "2",
    patientName: "Jane Smith",
    date: new Date(2023, 5, 16, 14, 30),
    doctor: "Dr. Johnson",
    reason: "Follow-up",
    status: "Scheduled",
  },
  {
    id: "3",
    patientName: "Bob Brown",
    date: new Date(2023, 5, 14, 11, 0),
    doctor: "Dr. Smith",
    reason: "Vaccination",
    status: "Completed",
  },
  {
    id: "4",
    patientName: "Alice Green",
    date: new Date(2023, 5, 17, 9, 0),
    doctor: "Dr. Davis",
    reason: "Consultation",
    status: "Scheduled",
  },
  {
    id: "5",
    patientName: "Charlie Wilson",
    date: new Date(2023, 5, 13, 16, 0),
    doctor: "Dr. Johnson",
    reason: "Test results",
    status: "Cancelled",
  },
];

export const patientsList = [
  "John Doe",
  "Jane Smith",
  "Bob Brown",
  "Alice Green",
  "Charlie Wilson",
];
export const doctorsList = ["Dr. Smith", "Dr. Johnson", "Dr. Davis"];

export default function AppointmentManagementScreen() {
  const [appointments, setAppointments] =
    useState<Appointment[]>(appointmentsData);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingAppointment, setEditingAppointment] =
    useState<Appointment | null>(null);
  const [newAppointment, setNewAppointment] = useState<Partial<Appointment>>({
    patientName: "",
    date: new Date(),
    doctor: "",
    reason: "",
    status: "Scheduled",
  });

  const handleEditAppointment = (appointment: Appointment) => {
    setEditingAppointment(appointment);
    setNewAppointment(appointment);
    setIsModalOpen(true);
  };

  const handleAddAppointment = () => {
    setEditingAppointment(null);
    setNewAppointment({
      patientName: "",
      date: new Date(),
      doctor: "",
      reason: "",
      status: "Scheduled",
    });
    setIsModalOpen(true);
  };

  const handleSaveAppointment = () => {
    if (
      newAppointment.patientName &&
      newAppointment.date &&
      newAppointment.doctor
    ) {
      if (editingAppointment) {
        setAppointments(
          appointments.map((app) =>
            app.id === editingAppointment.id
              ? { ...app, ...(newAppointment as Appointment) }
              : app
          )
        );
      } else {
        setAppointments([
          ...appointments,
          { id: Date.now().toString(), ...(newAppointment as Appointment) },
        ]);
      }
      setIsModalOpen(false);
    }
  };

  const handleCancelAppointment = (id: string) => {
    setAppointments(
      appointments.map((app) =>
        app.id === id ? { ...app, status: "Cancelled" } : app
      )
    );
  };

  const getStatusColor = (status: Appointment["status"]) => {
    switch (status) {
      case "Scheduled":
        return "bg-green-100 text-green-800";
      case "Completed":
        return "bg-blue-100 text-blue-800";
      case "Cancelled":
        return "bg-red-100 text-red-800";
      default:
        return "bg-gray-100 text-gray-800";
    }
  };

  return (
    <div className="container mx-auto p-4">
      <Card>
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-2xl font-bold">
            Appointment Management
          </CardTitle>
          <Button onClick={handleAddAppointment}>Add Appointment</Button>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Patient Name</TableHead>
                <TableHead>Date</TableHead>
                <TableHead>Doctor</TableHead>
                <TableHead>Reason</TableHead>
                <TableHead>Status</TableHead>
                <TableHead>Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {appointments.map((appointment) => (
                <TableRow key={appointment.id}>
                  <TableCell>{appointment.patientName}</TableCell>
                  <TableCell>{format(appointment.date, "PPP p")}</TableCell>
                  <TableCell>{appointment.doctor}</TableCell>
                  <TableCell>{appointment.reason}</TableCell>
                  <TableCell>
                    <Badge className={getStatusColor(appointment.status)}>
                      {appointment.status}
                    </Badge>
                  </TableCell>
                  <TableCell>
                    <div className="flex space-x-2">
                      <Button
                        variant="outline"
                        size="sm"
                        onClick={() => handleEditAppointment(appointment)}
                      >
                        Edit
                      </Button>
                      {appointment.status === "Scheduled" && (
                        <Button
                          variant="outline"
                          size="sm"
                          onClick={() =>
                            handleCancelAppointment(appointment.id)
                          }
                        >
                          Cancel
                        </Button>
                      )}
                    </div>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>

          <Dialog open={isModalOpen} onOpenChange={setIsModalOpen}>
            <DialogContent>
              <DialogHeader>
                <DialogTitle>
                  {editingAppointment ? "Edit Appointment" : "Add Appointment"}
                </DialogTitle>
              </DialogHeader>
              <div className="grid gap-4 py-4">
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="patient" className="text-right">
                    Patient
                  </Label>
                  <Select
                    value={newAppointment.patientName}
                    onValueChange={(value) =>
                      setNewAppointment({
                        ...newAppointment,
                        patientName: value,
                      })
                    }
                  >
                    <SelectTrigger className="col-span-3">
                      <SelectValue placeholder="Select patient" />
                    </SelectTrigger>
                    <SelectContent>
                      {patientsList.map((patient) => (
                        <SelectItem key={patient} value={patient}>
                          {patient}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="doctor" className="text-right">
                    Doctor
                  </Label>
                  <Select
                    value={newAppointment.doctor}
                    onValueChange={(value) =>
                      setNewAppointment({ ...newAppointment, doctor: value })
                    }
                  >
                    <SelectTrigger className="col-span-3">
                      <SelectValue placeholder="Select doctor" />
                    </SelectTrigger>
                    <SelectContent>
                      {doctorsList.map((doctor) => (
                        <SelectItem key={doctor} value={doctor}>
                          {doctor}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label className="text-right">Date</Label>
                  <Popover>
                    <PopoverTrigger asChild>
                      <Button
                        variant={"outline"}
                        className={`col-span-3 justify-start text-left font-normal ${
                          !newAppointment.date && "text-muted-foreground"
                        }`}
                      >
                        <CalendarIcon className="mr-2 h-4 w-4" />
                        {newAppointment.date ? (
                          format(newAppointment.date, "PPP")
                        ) : (
                          <span>Pick a date</span>
                        )}
                      </Button>
                    </PopoverTrigger>
                    <PopoverContent className="w-auto p-0">
                      <Calendar
                        mode="single"
                        selected={newAppointment.date}
                        onSelect={(date) =>
                          setNewAppointment({
                            ...newAppointment,
                            date: date || new Date(),
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
