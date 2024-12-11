"use client";
import { Button } from "@/components/ui/button";
import { useState } from "react";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import Link from "next/link";

import { useQuery, useSuspenseQuery } from "@tanstack/react-query";
import { createListHouseholdsQueryOptions } from "@/lib/apis/list-households.api";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
import { createListAllergiesQueryOptions } from "@/lib/apis/list-allergies.api";
import { useDeleteAllergyMutation } from "@/lib/apis/delete-allergy.api"; // Import the delete mutation
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog";

function DeleteAllergyButton({ allergyId }: { allergyId: string }) {
  const [isDialogOpen, setDialogOpen] = useState(false); // State for controlling dialog visibility
  const deleteMutation = useDeleteAllergyMutation({ id: allergyId }); // Mutation for deleting allergy

  // Handle the deletion on confirmation
  const handleDelete = () => {
    deleteMutation.mutate (undefined, {
      onSuccess: () => {
        setDialogOpen(false); // Close dialog on success
        // Optionally trigger refetch or other actions after deletion
      },
      onError: (error) => {
        // Optionally handle error
        console.error("Error deleting allergy:", error);
      },
    });
  };

  return (
    <>
      <AlertDialog open={isDialogOpen} onOpenChange={setDialogOpen}>
        <AlertDialogTrigger asChild>
          <Button className="ml-2 text-red-500 hover:underline">Delete</Button>
        </AlertDialogTrigger>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Are you absolutely sure?</AlertDialogTitle>
            <AlertDialogDescription>
              This action cannot be undone. This will permanently delete this allergy and remove your data from our system.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel onClick={() => setDialogOpen(false)}>Cancel</AlertDialogCancel>
            <AlertDialogAction onClick={handleDelete}>Delete</AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </>
  );
}


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

  // Hook for deleting allergy
  const deleteMutation = useDeleteAllergyMutation;

  const handleDelete = (id: string) => {
    if (window.confirm("Are you sure you want to delete this allergy?")) {
      deleteMutation({ id });
    }
  };

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
              <th className="border border-gray-300 p-2">Reaction Description</th>
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
                      <>
                        <Button asChild>
                          <Link
                            href={`/allergies/edit/${allergy.id}`}
                            className="text-blue-500 hover:underline"
                          >
                            Edit
                          </Link>
                        </Button>
                        <DeleteAllergyButton allergyId={allergy.id as string} />
                      </>
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
