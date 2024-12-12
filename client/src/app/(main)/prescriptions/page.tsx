"use client";

import { useQuery, useSuspenseQuery } from "@tanstack/react-query";
import Link from "next/link";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { CalendarIcon, PlusCircle, Pill, AlertCircle } from "lucide-react";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
import { createListPrescriptionsQueryOptions } from "@/lib/apis/list-prescriptions.api";
import { Button } from "@/components/ui/button";
import { Skeleton } from "@/components/ui/skeleton";

export default function ListPrescriptions() {
  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions(),
  );

  const listPrescriptionsQuery = useQuery(
    createListPrescriptionsQueryOptions({
      userId: currentUserInformationQuery.data?.userId,
    }),
  );

  const isLoading = listPrescriptionsQuery.isLoading;
  const isError = listPrescriptionsQuery.isError;
  const prescriptions = listPrescriptionsQuery.data?.content ?? [];

  return (
    <div className="container mx-auto py-8 px-4">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold">Your Prescriptions</h1>
        <Button asChild>
          <Link href="/prescriptions/create">
            <PlusCircle className="mr-2 h-4 w-4" />
            New Prescription
          </Link>
        </Button>
      </div>

      {isLoading ? (
        <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
          {[...Array(6)].map((_, index) => (
            <Card key={index} className="overflow-hidden">
              <CardHeader className="pb-2">
                <Skeleton className="h-4 w-1/3" />
              </CardHeader>
              <CardContent className="pb-2">
                <Skeleton className="h-4 w-2/3 mb-2" />
                <Skeleton className="h-4 w-1/2" />
              </CardContent>
              <CardFooter>
                <Skeleton className="h-4 w-full" />
              </CardFooter>
            </Card>
          ))}
        </div>
      ) : isError ? (
        <Card className="bg-destructive/10 text-destructive dark:bg-destructive dark:text-destructive-foreground">
          <CardHeader>
            <CardTitle className="flex items-center">
              <AlertCircle className="mr-2 h-5 w-5" />
              Error Loading Prescriptions
            </CardTitle>
          </CardHeader>
          <CardContent>
            <p>
              Failed to load prescriptions. Please try again later or contact
              support if the problem persists.
            </p>
          </CardContent>
          <CardFooter>
            <Button
              variant="outline"
              onClick={() => listPrescriptionsQuery.refetch()}
            >
              Retry
            </Button>
          </CardFooter>
        </Card>
      ) : prescriptions.length === 0 ? (
        <Card className="bg-muted">
          <CardHeader>
            <CardTitle className="flex items-center">
              <Pill className="mr-2 h-5 w-5" />
              No Prescriptions Found
            </CardTitle>
          </CardHeader>
          <CardContent>
            <p>
              You don't have any prescriptions yet. Create a new prescription to
              get started.
            </p>
          </CardContent>
          <CardFooter>
            <Button asChild>
              <Link href="/prescriptions/create">
                <PlusCircle className="mr-2 h-4 w-4" />
                Create New Prescription
              </Link>
            </Button>
          </CardFooter>
        </Card>
      ) : (
        <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
          {prescriptions.map((prescription) => (
            <Link
              href={`/prescriptions/${prescription.id}`}
              key={prescription.id}
              className="block"
            >
              <Card className="hover:shadow-lg transition-shadow h-full">
                <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                  <CardTitle className="text-lg font-medium">
                    Prescription {prescription.id.split("-")[0]}
                  </CardTitle>
                  <Badge
                    variant={
                      prescription.status === "ACTIVE" ? "default" : "secondary"
                    }
                    className="capitalize"
                  >
                    {prescription.status.toLowerCase()}
                  </Badge>
                </CardHeader>
                <CardContent>
                  <div className="text-sm text-muted-foreground mb-1">
                    Issued by
                  </div>
                  <div className="font-semibold">{prescription.issuedBy}</div>
                  <div className="mt-4 flex items-center text-sm text-muted-foreground">
                    <CalendarIcon className="mr-2 h-4 w-4" />
                    <span>
                      Valid until{" "}
                      {new Date(prescription.validUntil).toLocaleDateString()}
                    </span>
                  </div>
                </CardContent>
                <CardFooter>
                  <Button variant="ghost" className="w-full">
                    View Details
                  </Button>
                </CardFooter>
              </Card>
            </Link>
          ))}
        </div>
      )}
    </div>
  );
}
