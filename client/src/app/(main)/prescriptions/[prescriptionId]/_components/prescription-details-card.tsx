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
import { useQuery } from "@tanstack/react-query";
import { createGetPrescriptionDetailQueryOptions } from "@/lib/apis/get-prescription-detail.api";
import { createListPrescriptionItemsQueryOptions } from "@/lib/apis/list-prescription-item.api";

export function PrescriptionDetailsCard({
  prescriptionId,
}: {
  prescriptionId: string;
}) {
  const getPrescriptionDetailsQuery = useQuery(
    createGetPrescriptionDetailQueryOptions(prescriptionId),
  );
  const getPrescriptionItemsQuery = useQuery(
    createListPrescriptionItemsQueryOptions({ prescriptionId }),
  );

  const prescription = getPrescriptionDetailsQuery.data;
  const prescriptionItems = getPrescriptionItemsQuery.data?.content ?? [];

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
