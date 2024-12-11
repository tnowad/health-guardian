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
import { createListSurgeriesQueryOptions } from "@/lib/apis/list-surgeries.api"; // Custom API for surgeries
import { SurgerySchema } from "@/lib/schemas/surgery.schema"; // Import the Surgery schema

export default function SurgeryScreen() {
  // Get current user information
  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions()
  );
  
  // Get list of surgeries for the user
  const listSurgeriesQuery = useQuery(
    createListSurgeriesQueryOptions({
      userId: currentUserInformationQuery.data?.userId,
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
        <CardTitle>My Surgeries</CardTitle>
      </CardHeader>
      <CardContent>
        <Button asChild>
          <Link href="/surgeries/create">Create new surgery</Link>
        </Button>

        <table className="w-full table-auto mt-4 border-collapse border border-gray-300">
          <thead>
            <tr className="bg-gray-100">
              <th className="border border-gray-300 p-2">ID</th>
              <th className="border border-gray-300 p-2">Surgery Date</th>
              <th className="border border-gray-300 p-2">Description</th>
              <th className="border border-gray-300 p-2">Notes</th>
              <th className="border border-gray-300 p-2">Action</th>
            </tr>
          </thead>
          <tbody>
            {surgeries.length > 0 ? (
              surgeries.map((surgery: SurgerySchema) => (
                <tr key={surgery.id} className="hover:bg-gray-50">
                  <td className="border border-gray-300 p-2">{surgery.id}</td>
                  <td className="border border-gray-300 p-2">
                    {surgery.date ? new Date(surgery.date).toLocaleDateString() : "-"}
                  </td>
                  <td className="border border-gray-300 p-2">{surgery.description}</td>
                  <td className="border border-gray-300 p-2">{surgery.notes ?? "-"}</td>
                  <td className="border border-gray-300 p-2 text-center">
                    {currentUserInformationQuery.data.userId === surgery.userId ? (
                      <Button asChild>
                        <Link 
                          href={`/surgeries/edit/${surgery.id}`} 
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
