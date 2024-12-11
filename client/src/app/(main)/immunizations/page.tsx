"use client";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import Link from "next/link";
import { useQuery, useSuspenseQuery } from "@tanstack/react-query";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
import { createListImmunizationsQueryOptions } from "@/lib/apis/list-immunizations.api";

export default function ImmunizationScreen() {
  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions()
  );
  
  const listImmunizationsQuery = useQuery(
    createListImmunizationsQueryOptions({
      userId: currentUserInformationQuery.data?.userId,
    })
  );

  if (listImmunizationsQuery.error) {
    return (
      <Card>
        <CardHeader>
          <CardTitle>Error</CardTitle>
        </CardHeader>
        <CardContent>
          <p className="text-red-500">Failed to load immunizations. Please try again later.</p>
        </CardContent>
      </Card>
    );
  }

  const immunizations = listImmunizationsQuery.data?.content ?? [];
  
  return (
    <Card>
      <CardHeader>
        <CardTitle>My Immunizations</CardTitle>
      </CardHeader>
      <CardContent>
        <Button asChild>
          <Link href="/immunizations/create">Create new immunization</Link>
        </Button>

        <table className="w-full table-auto mt-4 border-collapse border border-gray-300">
          <thead>
            <tr className="bg-gray-100">
              <th className="border border-gray-300 p-2">ID</th>
              <th className="border border-gray-300 p-2">Vaccination Date</th>
              <th className="border border-gray-300 p-2">Vaccine Name</th>
              <th className="border border-gray-300 p-2">Batch Number</th>
              <th className="border border-gray-300 p-2">Note</th>
              <th className="border border-gray-300 p-2">Action</th>
            </tr>
          </thead>
          <tbody>
            {immunizations.length > 0 ? (
              immunizations.map((immunization) => (
                <tr key={immunization.id} className="hover:bg-gray-50">
                  <td className="border border-gray-300 p-2">{immunization.id}</td>
                  <td className="border border-gray-300 p-2">{immunization.vaccinationDate}</td>
                  <td className="border border-gray-300 p-2">{immunization.vaccineName}</td>
                  <td className="border border-gray-300 p-2">{immunization.batchNumber ?? "-"}</td>
                  <td className="border border-gray-300 p-2">{immunization.notes ?? "-"}</td>
                  <td className="border border-gray-300 p-2 text-center">
                    {currentUserInformationQuery.data.userId === immunization.userId ? (
                      <Button asChild>
                        <Link 
                          href={`/immunizations/edit/${immunization.id}`} 
                          className="text-blue-500 hover:underline"
                        >
                          Edit
                        </Link>
                      </Button>
                    ) : (
                      "-"
                    )}
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td className="border border-gray-300 p-2" colSpan={6}>
                  No immunizations found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </CardContent>
    </Card>
  );
}
