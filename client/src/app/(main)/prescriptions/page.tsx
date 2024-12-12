"use client";

import { useQuery, useSuspenseQuery } from "@tanstack/react-query";
import Link from "next/link";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { CalendarIcon } from "lucide-react";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
import { createListPrescriptionsQueryOptions } from "@/lib/apis/list-prescriptions.api";
import { Button } from "@/components/ui/button";

export default function ListPrescriptions() {
  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions(),
  );

  const listPrescriptionsQuery = useQuery(
    createListPrescriptionsQueryOptions({
      userId: currentUserInformationQuery.data?.userId,
    }),
  );

  if (listPrescriptionsQuery.isLoading) {
    return (
      <div className="container mx-auto p-4">Loading prescriptions...</div>
    );
  }

  if (listPrescriptionsQuery.isError) {
    return (
      <div className="container mx-auto p-4 text-red-600">
        Failed to load prescriptions. Please try again later.
      </div>
    );
  }

  const prescriptions = listPrescriptionsQuery.data?.content ?? [];

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Your Prescriptions</h1>
      <Button asChild className="w-full">
        <Link href={`/prescriptions/create`}>Create new prescription</Link>
      </Button>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
        {prescriptions.map((prescription) => (
          <Link
            href={`/prescriptions/${prescription.id}`}
            key={prescription.id}
          >
            <Card className="hover:shadow-lg transition-shadow">
              <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                <CardTitle className="text-sm font-medium">
                  Prescription {prescription.id.split("-")[0]}
                </CardTitle>
                <Badge
                  variant={
                    prescription.status === "ACTIVE" ? "default" : "secondary"
                  }
                >
                  {prescription.status}
                </Badge>
              </CardHeader>
              <CardContent>
                <div className="text-xs text-muted-foreground">Issued by</div>
                <div className="font-semibold">{prescription.issuedBy}</div>
                <div className="mt-2 flex items-center text-xs text-muted-foreground">
                  <CalendarIcon className="mr-1 h-3 w-3" />
                  <span>Valid until {prescription.validUntil}</span>
                </div>
              </CardContent>
            </Card>
          </Link>
        ))}
      </div>
    </div>
  );
}
