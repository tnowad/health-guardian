import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { PillIcon, ClockIcon } from "lucide-react";
import { PrescriptionItem } from "@/types/prescription";

// Mock data for demonstration
const mockPendingItems: PrescriptionItem[] = [
  {
    id: "item1",
    prescriptionId: "123e4567-e89b-12d3-a456-426614174000",
    dosage: "10mg",
    medicationName: "Ibuprofen",
    image: "/placeholder.svg?height=50&width=50",
    frequency: "Every 6 hours",
    startDate: "2023-06-01",
    endDate: "2023-06-07",
    status: "PENDING",
  },
  {
    id: "item2",
    prescriptionId: "223e4567-e89b-12d3-a456-426614174001",
    dosage: "500mg",
    medicationName: "Amoxicillin",
    image: "/placeholder.svg?height=50&width=50",
    frequency: "Twice daily",
    startDate: "2023-06-01",
    endDate: "2023-06-10",
    status: "PENDING",
  },
];

export default function ListPendingPrescriptionItems() {
  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Pending Prescription Items</h1>
      <div className="space-y-4">
        {mockPendingItems.map((item) => (
          <Card key={item.id}>
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
              <div className="flex flex-col items-end">
                <Badge variant="secondary" className="mb-2">
                  {item.status}
                </Badge>
                <Button size="sm">Take Now</Button>
              </div>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
}
