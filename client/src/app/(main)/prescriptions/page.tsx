"use client";

import { useState } from "react";
import { format } from "date-fns";
import {
  ChevronDown,
  ChevronUp,
  Plus,
  CheckCircle,
  XCircle,
  AlertCircle,
} from "lucide-react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
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
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Calendar } from "@/components/ui/calendar";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { useQuery, useSuspenseQuery } from "@tanstack/react-query";
import { createListPrescriptionsQueryOptions } from "@/lib/apis/list-prescriptions.api";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
import { createListMedicationsQueryOptions } from "@/lib/apis/list-medications.api";

interface Prescription {
  id: string;
  medication: string;
  dosage: string;
  frequency: string;
  startDate: Date;
  endDate: Date;
  instructions: string;
  status: "active" | "expired" | "completed";
}

const initialPrescriptions: Prescription[] = [
  {
    id: "1",
    medication: "Amoxicillin",
    dosage: "500mg",
    frequency: "Every 8 hours",
    startDate: new Date(2023, 5, 1),
    endDate: new Date(2023, 5, 10),
    instructions:
      "Take with food. Complete the full course even if you feel better.",
    status: "completed",
  },
  {
    id: "2",
    medication: "Lisinopril",
    dosage: "10mg",
    frequency: "Once daily",
    startDate: new Date(2023, 4, 15),
    endDate: new Date(2023, 7, 15),
    instructions: "Take in the morning. Avoid grapefruit juice.",
    status: "active",
  },
  {
    id: "3",
    medication: "Ibuprofen",
    dosage: "400mg",
    frequency: "Every 6 hours as needed",
    startDate: new Date(2023, 5, 5),
    endDate: new Date(2023, 5, 20),
    instructions: "Take with food or milk. Do not exceed 1200mg in 24 hours.",
    status: "expired",
  },
];

const medications = [
  "Amoxicillin",
  "Lisinopril",
  "Ibuprofen",
  "Metformin",
  "Simvastatin",
];

export default function PrescriptionsScreen() {
  const getCurrentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions()
  );
  const getMedicationsQuery = useQuery(createListMedicationsQueryOptions({}));

  const listPrescriptionsQuery = useQuery(
    createListPrescriptionsQueryOptions({
      patientId: getCurrentUserInformationQuery.data.patient?.id,
    })
  );

  const [prescriptions, setPrescriptions] =
    useState<Prescription[]>(initialPrescriptions);
  const [expandedRows, setExpandedRows] = useState<Set<string>>(new Set());
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [newPrescription, setNewPrescription] = useState<Partial<Prescription>>(
    {
      medication: "",
      dosage: "",
      frequency: "",
      startDate: new Date(),
      endDate: new Date(),
      instructions: "",
      status: "active",
    }
  );

  const toggleRow = (id: string) => {
    const newExpandedRows = new Set(expandedRows);
    if (newExpandedRows.has(id)) {
      newExpandedRows.delete(id);
    } else {
      newExpandedRows.add(id);
    }
    setExpandedRows(newExpandedRows);
  };

  const handleAddPrescription = () => {
    if (
      newPrescription.medication &&
      newPrescription.dosage &&
      newPrescription.startDate &&
      newPrescription.endDate
    ) {
      setPrescriptions([
        ...prescriptions,
        {
          id: Date.now().toString(),
          ...(newPrescription as Prescription),
        },
      ]);
      setIsAddModalOpen(false);
      setNewPrescription({
        medication: "",
        dosage: "",
        frequency: "",
        startDate: new Date(),
        endDate: new Date(),
        instructions: "",
        status: "active",
      });
    }
  };

  const getStatusIcon = (status: Prescription["status"]) => {
    switch (status) {
      case "active":
        return <CheckCircle className="h-5 w-5 text-green-500" />;
      case "expired":
        return <XCircle className="h-5 w-5 text-red-500" />;
      case "completed":
        return <AlertCircle className="h-5 w-5 text-yellow-500" />;
    }
  };

  return (
    <Card>
      <CardHeader className="flex flex-row items-center justify-between">
        <CardTitle className="text-2xl font-bold">Prescriptions</CardTitle>
        <Dialog open={isAddModalOpen} onOpenChange={setIsAddModalOpen}>
          <DialogTrigger asChild>
            <Button>
              <Plus className="mr-2 h-4 w-4" /> Add Prescription
            </Button>
          </DialogTrigger>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Add New Prescription</DialogTitle>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="medication" className="text-right">
                  Medication
                </Label>
                <Select
                  value={newPrescription.medication}
                  onValueChange={(value) =>
                    setNewPrescription({
                      ...newPrescription,
                      medication: value,
                    })
                  }
                >
                  <SelectTrigger className="col-span-3">
                    <SelectValue placeholder="Select medication" />
                  </SelectTrigger>
                  <SelectContent>
                    {getMedicationsQuery.data?.content.map((med) => (
                      <SelectItem key={med.id} value={med.id}>
                        {med.name}
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="dosage" className="text-right">
                  Dosage
                </Label>
                <Input
                  id="dosage"
                  value={newPrescription.dosage}
                  onChange={(e) =>
                    setNewPrescription({
                      ...newPrescription,
                      dosage: e.target.value,
                    })
                  }
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="frequency" className="text-right">
                  Frequency
                </Label>
                <Input
                  id="frequency"
                  value={newPrescription.frequency}
                  onChange={(e) =>
                    setNewPrescription({
                      ...newPrescription,
                      frequency: e.target.value,
                    })
                  }
                  className="col-span-3"
                />
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label className="text-right">Start Date</Label>
                <Popover>
                  <PopoverTrigger asChild>
                    <Button
                      variant={"outline"}
                      className={`col-span-3 justify-start text-left font-normal ${
                        !newPrescription.startDate && "text-muted-foreground"
                      }`}
                    >
                      {newPrescription.startDate ? (
                        format(newPrescription.startDate, "PPP")
                      ) : (
                        <span>Pick a date</span>
                      )}
                    </Button>
                  </PopoverTrigger>
                  <PopoverContent className="w-auto p-0">
                    <Calendar
                      mode="single"
                      selected={newPrescription.startDate}
                      onSelect={(date) =>
                        setNewPrescription({
                          ...newPrescription,
                          startDate: date || new Date(),
                        })
                      }
                      initialFocus
                    />
                  </PopoverContent>
                </Popover>
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label className="text-right">End Date</Label>
                <Popover>
                  <PopoverTrigger asChild>
                    <Button
                      variant={"outline"}
                      className={`col-span-3 justify-start text-left font-normal ${
                        !newPrescription.endDate && "text-muted-foreground"
                      }`}
                    >
                      {newPrescription.endDate ? (
                        format(newPrescription.endDate, "PPP")
                      ) : (
                        <span>Pick a date</span>
                      )}
                    </Button>
                  </PopoverTrigger>
                  <PopoverContent className="w-auto p-0">
                    <Calendar
                      mode="single"
                      selected={newPrescription.endDate}
                      onSelect={(date) =>
                        setNewPrescription({
                          ...newPrescription,
                          endDate: date || new Date(),
                        })
                      }
                      initialFocus
                    />
                  </PopoverContent>
                </Popover>
              </div>
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="instructions" className="text-right">
                  Instructions
                </Label>
                <Input
                  id="instructions"
                  value={newPrescription.instructions}
                  onChange={(e) =>
                    setNewPrescription({
                      ...newPrescription,
                      instructions: e.target.value,
                    })
                  }
                  className="col-span-3"
                />
              </div>
            </div>
            <div className="flex justify-end">
              <Button onClick={handleAddPrescription}>Save Prescription</Button>
            </div>
          </DialogContent>
        </Dialog>
      </CardHeader>
      <CardContent>
        <div className="space-y-4">
          {listPrescriptionsQuery.data?.content.map((prescription) => (
            <div key={prescription.id} className="border rounded-lg p-4">
              <div className="flex items-center justify-between">
                <div className="flex items-center space-x-2">
                  {getStatusIcon(prescription.status)}
                  <span className="font-medium">{prescription.medication}</span>
                </div>
                <Button
                  variant="ghost"
                  size="sm"
                  onClick={() => toggleRow(prescription.id)}
                  aria-expanded={expandedRows.has(prescription.id)}
                  aria-controls={`prescription-details-${prescription.id}`}
                >
                  {expandedRows.has(prescription.id) ? (
                    <ChevronUp className="h-4 w-4" />
                  ) : (
                    <ChevronDown className="h-4 w-4" />
                  )}
                  <span className="sr-only">Toggle details</span>
                </Button>
              </div>
              <div className="mt-2 text-sm text-gray-600">
                <span>{prescription.dosage}</span> •{" "}
                <span>{prescription.frequency}</span>
              </div>
              <div className="mt-1 text-sm text-gray-500">
                {format(prescription.startDate, "MMM d, yyyy")} -{" "}
                {format(prescription.endDate, "MMM d, yyyy")}
              </div>
              {expandedRows.has(prescription.id) && (
                <div
                  id={`prescription-details-${prescription.id}`}
                  className="mt-4 text-sm text-gray-700"
                >
                  <p>
                    <strong>Instructions:</strong> {prescription.instructions}
                  </p>
                </div>
              )}
            </div>
          ))}
        </div>
      </CardContent>
    </Card>
  );
}
