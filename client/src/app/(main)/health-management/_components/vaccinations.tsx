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
import { createListImmunizationsQueryOptions } from "@/lib/apis/list-immunizations.api"; // Custom API for surgeries
import { ImmunizationSchema } from "@/lib/schemas/immunization.schema"; // Import the Surgery schema
import { useSearchParams } from "next/navigation";
export default function VaccinationScreen() {
  // Get current user information

    const searchParams = useSearchParams()
    const userIdSearch = searchParams.get('as')

  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions()
  );
  
  // Get list of surgeries for the user
  const listSurgeriesQuery = useQuery(
    createListImmunizationsQueryOptions({
        userId: userIdSearch ||  currentUserInformationQuery.data?.userId,
    })
  );

  // Handle API error
  if (listSurgeriesQuery.error) {
    return (
      <Card>
        <CardHeader>
          <CardTitle>Error</CardTitle>
        </CardHeader>
        <CardContent>
          <p className="text-red-500">Failed to load surgeries. Please try again later.</p>
        </CardContent>
      </Card>
    );
  }

  const surgeries = listSurgeriesQuery.data?.content ?? [];
  
  return (
    <Card>
      <CardHeader>
        <CardTitle>My Vaccinations</CardTitle>
      </CardHeader>
      <CardContent>
        

        <table className="w-full table-auto mt-4 border-collapse border border-gray-300">
          <thead>
            <tr className="bg-gray-100">
              <th className="border border-gray-300 p-2">Vaccine Name</th>
              <th className="border border-gray-300 p-2">Vaccine Date</th>
              <th className="border border-gray-300 p-2">Batch Number</th>
              <th className="border border-gray-300 p-2">Note</th>
            </tr>
          </thead>
          <tbody>
            {surgeries.length > 0 ? (
              surgeries.map((surgery: ImmunizationSchema) => (
                <tr key={surgery.id} className="hover:bg-gray-50">
                  
                  <td className="border border-gray-300 p-2">
                    {surgery.vaccineName}
                  </td>
                  <td className="border border-gray-300 p-2">{surgery.vaccinationDate}</td>
                  <td className="border border-gray-300 p-2">{surgery.batchNumber}</td>
                  <td className="border border-gray-300 p-2 text-center">
                    {surgery.notes}
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td className="border border-gray-300 p-2" colSpan={5}>
                  No surgeries found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </CardContent>
    </Card>
  );
}
