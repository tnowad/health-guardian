"use client";

import { useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";

interface Prescription {
  id: string;
  name: string;
  dosage: string;
  frequency: string;
  startDate: string;
  endDate: string;
  instructions: string;
}

const mockPrescriptions: Prescription[] = [
  {
    id: "1",
    name: "Lisinopril",
    dosage: "10mg",
    frequency: "Once daily",
    startDate: "2023-01-15",
    endDate: "2023-07-15",
    instructions:
      "Take in the morning with or without food. Avoid potassium supplements.",
  },
  {
    id: "2",
    name: "Metformin",
    dosage: "500mg",
    frequency: "Twice daily",
    startDate: "2023-02-01",
    endDate: "2023-08-01",
    instructions: "Take with meals. May cause stomach upset initially.",
  },
];

export default function PrescriptionsList() {
  const [expandedPrescription, setExpandedPrescription] = useState<
    string | null
  >(null);

  const toggleExpand = (id: string) => {
    setExpandedPrescription(expandedPrescription === id ? null : id);
  };

  return (
    <Card>
      <CardHeader>
        <CardTitle>Prescriptions</CardTitle>
      </CardHeader>
      <CardContent>
        <ul className="space-y-4">
          {mockPrescriptions.map((prescription) => (
            <li key={prescription.id} className="p-4 bg-gray-100 rounded-lg">
              <div className="flex justify-between items-center">
                <div>
                  <p className="font-semibold">{prescription.name}</p>
                  <p>
                    {prescription.dosage}, {prescription.frequency}
                  </p>
                  <p className="text-sm text-gray-600">
                    {prescription.startDate} to {prescription.endDate}
                  </p>
                </div>
                <Button
                  variant="ghost"
                  size="sm"
                  onClick={() => toggleExpand(prescription.id)}
                >
                  {expandedPrescription === prescription.id
                    ? "Hide Details"
                    : "Show Details"}
                </Button>
              </div>
              {expandedPrescription === prescription.id && (
                <div className="mt-4 p-4 bg-white rounded-md">
                  <h4 className="font-semibold mb-2">Instructions:</h4>
                  <p>{prescription.instructions}</p>
                </div>
              )}
            </li>
          ))}
        </ul>
      </CardContent>
    </Card>
  );
}
