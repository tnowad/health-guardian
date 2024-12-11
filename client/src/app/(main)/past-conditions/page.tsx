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
import { createListPastConditionsQueryOptions } from "@/lib/apis/list-past-conditions.api"; // Custom API for past conditions
import { PastConditionSchema } from "@/lib/schemas/past-condition.schema"; // Import the PastCondition schema

export default function PastConditionScreen() {
  // Get current user information
  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions()
  );
  
  // Get list of past conditions for the user
  const listPastConditionsQuery = useQuery(
    createListPastConditionsQueryOptions({
      userId: currentUserInformationQuery.data?.userId,
    })
  );



  // Handle API error
  if (listPastConditionsQuery.error) {
    return (
      <Card>
        <CardHeader>
          <CardTitle>Error</CardTitle>
        </CardHeader>
        <CardContent>
          <p className="text-red-500">Failed to load past conditions. Please try again later.</p>
        </CardContent>
      </Card>
    );
  }

  const pastConditions = listPastConditionsQuery.data?.content ?? [];
  
  return (
    <Card>
      <CardHeader>
        <CardTitle>My Past Conditions</CardTitle>
      </CardHeader>
      <CardContent>
        <Button asChild>
          <Link href="/past-conditions/create">Create new condition</Link>
        </Button>

        <table className="w-full table-auto mt-4 border-collapse border border-gray-300">
          <thead>
            <tr className="bg-gray-100">
              <th className="border border-gray-300 p-2">ID</th>
              <th className="border border-gray-300 p-2">Condition Name</th>
              <th className="border border-gray-300 p-2">Description</th>
              <th className="border border-gray-300 p-2">Date Diagnosed</th>
              <th className="border border-gray-300 p-2">Action</th>
            </tr>
          </thead>
          <tbody>
            {pastConditions.length > 0 ? (
              pastConditions.map((condition: PastConditionSchema) => (
                <tr key={condition.id} className="hover:bg-gray-50">
                  <td className="border border-gray-300 p-2">{condition.id}</td>
                  <td className="border border-gray-300 p-2">{condition.condition}</td>
                  <td className="border border-gray-300 p-2">{condition.description ?? "-"}</td>
                  <td className="border border-gray-300 p-2">
                    {condition.dateDiagnosed 
                      ? new Date(condition.dateDiagnosed).toLocaleDateString() 
                      : "-"
                    }
                  </td>
                  <td className="border border-gray-300 p-2 text-center">
                    {currentUserInformationQuery.data.userId === condition.userId ? (
                      <Button asChild>
                        <Link 
                          href={`/past-conditions/edit/${condition.id}`} 
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
                  No past conditions found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </CardContent>
    </Card>
  );
}
