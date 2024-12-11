"use client";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import Link from "next/link";

import { AspectRatio } from "@/components/ui/aspect-ratio";
import Image from "next/image";
import { useQuery, useSuspenseQuery } from "@tanstack/react-query";
import { createListHouseholdsQueryOptions } from "@/lib/apis/list-households.api";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
import { createListHouseholdMembersQueryOptions } from "@/lib/apis/list-household-members.api";
import { createListAllergiesQueryOptions } from "@/lib/apis/list-allergies.api";

export default function AllergyScreen() {
  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions(),
  );

  const listAllergiesQuery = useQuery(
    createListAllergiesQueryOptions({
      userId: currentUserInformationQuery.data?.userId,
    }),
  );
  console.log(listAllergiesQuery.error);

  const allergies = listAllergiesQuery.data?.content ?? [];

  return (
    <Card>
      <CardHeader>
        <CardTitle>My Allergies</CardTitle>
      </CardHeader>
      <CardContent>
        <Button asChild>
          <Link href="/allergies/create">Create new allergy</Link>
        </Button>

        <table className="w-full table-auto mt-4 border-collapse border border-gray-300">
          <thead>
            <tr className="bg-gray-100">
              <th className="border border-gray-300 p-2">ID</th>
              <th className="border border-gray-300 p-2">Allergy Name</th>
              <th className="border border-gray-300 p-2">
                Reaction Description
              </th>
              <th className="border border-gray-300 p-2">Severity</th>
              <th className="border border-gray-300 p-2">Action</th>
            </tr>
          </thead>
          <tbody>
            {allergies.length > 0 ? (
              allergies.map((allergy) => (
                <tr key={allergy.id} className="hover:bg-gray-50">
                  <td className="border border-gray-300 p-2">{allergy.id}</td>
                  <td className="border border-gray-300 p-2">
                    {allergy.allergyName}
                  </td>
                  <td className="border border-gray-300 p-2">
                    {allergy.reactionDescription}
                  </td>
                  <td className="border border-gray-300 p-2">
                    {allergy.severity}
                  </td>
                  <td className="border border-gray-300 p-2 text-center">
                    {currentUserInformationQuery.data.userId ===
                    allergy.userId ? (
                      <Link
                        href={`/allergies/${allergy.id}`}
                        className="text-blue-500 hover:underline"
                      >
                        Edit
                      </Link>
                    ) : (
                      "-"
                    )}
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td className="border border-gray-300 p-2" colSpan={5}>
                  No allergies found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </CardContent>
    </Card>
  );
}
