"use client";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { PillIcon, ClockIcon } from "lucide-react";
import { createListPrescriptionItemsQueryOptions } from "@/lib/apis/list-prescription-item.api";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
import { useSuspenseQuery, useQuery } from "@tanstack/react-query";
import { use } from "react";

export default function ListPendingPrescriptionItems() {
  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions(),
  );

  const listPendingPrescriptionItemsQuery = useQuery(
    createListPrescriptionItemsQueryOptions({
      userId: currentUserInformationQuery.data?.userId,
    }),
  );

  if (listPendingPrescriptionItemsQuery.isLoading) {
    return (
      <div className="container mx-auto p-4">Loading pending items...</div>
    );
  }

  if (listPendingPrescriptionItemsQuery.isError) {
    return (
      <div className="container mx-auto p-4 text-red-600">
        Failed to load pending items. Please try again later.
      </div>
    );
  }

  const pendingItems = listPendingPrescriptionItemsQuery.data?.content ?? [];

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Pending Prescription Items</h1>
      <div className="space-y-4">
        {pendingItems.map((item) => (
          <Card key={item.id}>
            <CardContent className="flex items-center p-4">
              <Avatar className="h-16 w-16 mr-4">
                <AvatarImage
                  src={item.image || "/images/default-medication.png"}
                  alt={item.medicationName}
                />
                <AvatarFallback>
                  <PillIcon className="h-8 w-8" />
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
                <Badge className="mb-2">{item.status}</Badge>
                <Button size="sm">Take Now</Button>
              </div>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
}
