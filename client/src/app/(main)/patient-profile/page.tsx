"use client";

import { useState } from "react";
import { format } from "date-fns";
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
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";

export interface PatientInfo {
  id: string;
  name: string;
  dateOfBirth: string;
  gender: string;
  contactDetails: {
    phone: string;
    email: string;
    address: string;
  };
  guardian?: {
    name: string;
    relationship: string;
    phone: string;
  };
}

export interface HealthSummaryItem {
  id: string;
  category: "diagnosis" | "allergy" | "healthHistory";
  title: string;
  details: string;
  date: string;
}

export const patientData: PatientInfo = {
  id: "12345",
  name: "Jane Doe",
  dateOfBirth: "1985-05-15",
  gender: "Female",
  contactDetails: {
    phone: "(555) 123-4567",
    email: "jane.doe@example.com",
    address: "123 Main St, Anytown, USA 12345",
  },
  guardian: {
    name: "John Doe",
    relationship: "Spouse",
    phone: "(555) 987-6543",
  },
};

export const healthSummaryData: HealthSummaryItem[] = [
  {
    id: "1",
    category: "diagnosis",
    title: "Type 2 Diabetes",
    details:
      "Diagnosed 5 years ago. Currently managed with medication and diet.",
    date: "2018-06-10",
  },
  {
    id: "2",
    category: "allergy",
    title: "Penicillin",
    details:
      "Severe allergic reaction. Avoid all penicillin-based antibiotics.",
    date: "2010-03-22",
  },
  {
    id: "3",
    category: "healthHistory",
    title: "Appendectomy",
    details: "Surgical removal of appendix. No complications.",
    date: "2005-11-30",
  },
  {
    id: "4",
    category: "diagnosis",
    title: "Hypertension",
    details:
      "Mild hypertension. Managed with lifestyle changes and medication.",
    date: "2020-09-15",
  },
  {
    id: "5",
    category: "allergy",
    title: "Peanuts",
    details: "Moderate allergic reaction. Avoid all peanut products.",
    date: "2000-07-18",
  },
];

export default function PatientProfileScreen() {
  const [patient, setPatient] = useState<PatientInfo>(patientData);
  const [healthSummary, setHealthSummary] =
    useState<HealthSummaryItem[]>(healthSummaryData);
  const [isEditGuardianOpen, setIsEditGuardianOpen] = useState(false);
  const [editedGuardian, setEditedGuardian] = useState(
    patient.guardian || { name: "", relationship: "", phone: "" }
  );

  const handleSaveGuardian = () => {
    setPatient({ ...patient, guardian: editedGuardian });
    setIsEditGuardianOpen(false);
  };

  return (
    <div className="container mx-auto p-4">
      <Card className="mb-6">
        <CardHeader>
          <CardTitle className="text-2xl font-bold">Patient Profile</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="grid md:grid-cols-2 gap-4">
            <div>
              <h3 className="text-lg font-semibold mb-2">
                Personal Information
              </h3>
              <p>
                <strong>Name:</strong> {patient.name}
              </p>
              <p>
                <strong>Date of Birth:</strong>{" "}
                {format(new Date(patient.dateOfBirth), "MMMM d, yyyy")}
              </p>
              <p>
                <strong>Gender:</strong> {patient.gender}
              </p>
            </div>
            <div>
              <h3 className="text-lg font-semibold mb-2">Contact Details</h3>
              <p>
                <strong>Phone:</strong> {patient.contactDetails.phone}
              </p>
              <p>
                <strong>Email:</strong> {patient.contactDetails.email}
              </p>
              <p>
                <strong>Address:</strong> {patient.contactDetails.address}
              </p>
            </div>
          </div>
          <div className="mt-4">
            <h3 className="text-lg font-semibold mb-2">Guardian Information</h3>
            {patient.guardian ? (
              <>
                <p>
                  <strong>Name:</strong> {patient.guardian.name}
                </p>
                <p>
                  <strong>Relationship:</strong> {patient.guardian.relationship}
                </p>
                <p>
                  <strong>Phone:</strong> {patient.guardian.phone}
                </p>
              </>
            ) : (
              <p>No guardian information available.</p>
            )}
            <Dialog
              open={isEditGuardianOpen}
              onOpenChange={setIsEditGuardianOpen}
            >
              <DialogTrigger asChild>
                <Button className="mt-2">
                  {patient.guardian ? "Edit" : "Add"} Guardian Info
                </Button>
              </DialogTrigger>
              <DialogContent>
                <DialogHeader>
                  <DialogTitle>
                    {patient.guardian ? "Edit" : "Add"} Guardian Information
                  </DialogTitle>
                </DialogHeader>
                <div className="grid gap-4 py-4">
                  <div className="grid grid-cols-4 items-center gap-4">
                    <Label htmlFor="guardianName" className="text-right">
                      Name
                    </Label>
                    <Input
                      id="guardianName"
                      value={editedGuardian.name}
                      onChange={(e) =>
                        setEditedGuardian({
                          ...editedGuardian,
                          name: e.target.value,
                        })
                      }
                      className="col-span-3"
                    />
                  </div>
                  <div className="grid grid-cols-4 items-center gap-4">
                    <Label
                      htmlFor="guardianRelationship"
                      className="text-right"
                    >
                      Relationship
                    </Label>
                    <Input
                      id="guardianRelationship"
                      value={editedGuardian.relationship}
                      onChange={(e) =>
                        setEditedGuardian({
                          ...editedGuardian,
                          relationship: e.target.value,
                        })
                      }
                      className="col-span-3"
                    />
                  </div>
                  <div className="grid grid-cols-4 items-center gap-4">
                    <Label htmlFor="guardianPhone" className="text-right">
                      Phone
                    </Label>
                    <Input
                      id="guardianPhone"
                      value={editedGuardian.phone}
                      onChange={(e) =>
                        setEditedGuardian({
                          ...editedGuardian,
                          phone: e.target.value,
                        })
                      }
                      className="col-span-3"
                    />
                  </div>
                </div>
                <div className="flex justify-end space-x-2">
                  <Button
                    variant="outline"
                    onClick={() => setIsEditGuardianOpen(false)}
                  >
                    Cancel
                  </Button>
                  <Button onClick={handleSaveGuardian}>Save</Button>
                </div>
              </DialogContent>
            </Dialog>
          </div>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle className="text-xl font-semibold">
            Health Summary
          </CardTitle>
        </CardHeader>
        <CardContent>
          <Accordion type="single" collapsible className="w-full">
            {healthSummary.map((item) => (
              <AccordionItem key={item.id} value={item.id}>
                <AccordionTrigger>
                  <div className="flex justify-between w-full">
                    <span>{item.title}</span>
                    <span className="text-sm text-gray-500">
                      {format(new Date(item.date), "MMM d, yyyy")}
                    </span>
                  </div>
                </AccordionTrigger>
                <AccordionContent>
                  <p>
                    <strong>Category:</strong> {item.category}
                  </p>
                  <p>
                    <strong>Details:</strong> {item.details}
                  </p>
                </AccordionContent>
              </AccordionItem>
            ))}
          </Accordion>
        </CardContent>
      </Card>
    </div>
  );
}
