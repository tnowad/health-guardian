"use client";

import { useState, useEffect } from "react";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { CalendarIcon, PillIcon, ClockIcon } from "lucide-react";
import { Prescription, PrescriptionItem } from "./types/prescription";

// Mock data for demonstration
const mockPrescription: Prescription = {
  id: "123e4567-e89b-12d3-a456-426614174000",
  userId: "98765432-e89b-12d3-a456-426614174000",
  issuedBy: "Dr. John Doe",
  validUntil: "2023-12-31",
  issuedDate: "2023-06-01",
  status: "ACTIVE",
};

const mockPrescriptionItems: PrescriptionItem[] = [
  {
    id: "item1",
    prescriptionId: "123e4567-e89b-12d3-a456-426614174000",
    dosage: "10mg",
    medicationName: "Ibuprofen",
    image: "/placeholder.svg?height=50&width=50",
    frequency: "Every 6 hours",
    startDate: "2023-06-01",
    endDate: "2023-06-07",
    status: "ACTIVE",
  },
  {
    id: "item2",
    prescriptionId: "123e4567-e89b-12d3-a456-426614174000",
    dosage: "500mg",
    medicationName: "Amoxicillin",
    image: "/placeholder.svg?height=50&width=50",
    frequency: "Twice daily",
    startDate: "2023-06-01",
    endDate: "2023-06-10",
    status: "ACTIVE",
  },
];

export default function PrescriptionScreen() {
  const [prescription, setPrescription] = useState<Prescription | null>(null);
  const [prescriptionItems, setPrescriptionItems] = useState<
    PrescriptionItem[]
  >([]);

  useEffect(() => {
    // In a real application, you would fetch this data from an API
    setPrescription(mockPrescription);
    setPrescriptionItems(mockPrescriptionItems);
  }, []);

  if (!prescription) {
    return <div>Loading...</div>;
  }

  return (
    <div className="container mx-auto p-4">
      <Card className="mb-8">
        <CardHeader>
          <CardTitle>Prescription Details</CardTitle>
          <CardDescription>Issued by {prescription.issuedBy}</CardDescription>
        </CardHeader>
        <CardContent>
          <div className="grid grid-cols-2 gap-4">
            <div className="flex items-center">
              <CalendarIcon className="mr-2" />
              <span>Issued: {prescription.issuedDate}</span>
            </div>
            <div className="flex items-center">
              <CalendarIcon className="mr-2" />
              <span>Valid until: {prescription.validUntil}</span>
            </div>
            <div className="flex items-center">
              <Badge
                variant={
                  prescription.status === "ACTIVE" ? "default" : "secondary"
                }
              >
                {prescription.status}
              </Badge>
            </div>
          </div>
        </CardContent>
      </Card>

      <h2 className="text-2xl font-bold mb-4">Prescription Items</h2>
      {prescriptionItems.map((item) => (
        <Card key={item.id} className="mb-4">
          <CardContent className="flex items-center p-4">
            <Avatar className="h-16 w-16 mr-4">
              <AvatarImage src={item.image} alt={item.medicationName} />
              <AvatarFallback>
                <PillIcon />
              </AvatarFallback>
            </Avatar>
            <div className="flex-grow">
              <h3 className="text-lg font-semibold">{item.medicationName}</h3>
              <p className="text-sm text-gray-500">{item.dosage}</p>
              <div className="flex items-center mt-2">
                <ClockIcon className="mr-2 h-4 w-4" />
                <span className="text-sm">{item.frequency}</span>
              </div>
            </div>
            <Badge variant={item.status === "ACTIVE" ? "default" : "secondary"}>
              {item.status}
            </Badge>
          </CardContent>
        </Card>
      ))}
    </div>
  );
}
