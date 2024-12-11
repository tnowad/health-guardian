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
import { createListUsersQueryOptions } from "@/lib/apis/list-users.api";

import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { useMemo } from "react";
import { createGetHouseholdDetailQueryOptions } from "@/lib/apis/get-household-detail.api";
import { useToast } from "@/hooks/use-toast";
type HouseholdDetailsCardProps = {
  householdId: string;
};
export function HouseholdDetailsCard({
  householdId,
}: HouseholdDetailsCardProps) {
  const { toast } = useToast();
  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions(),
  );

  const listHouseholdMembersQuery = useQuery(
    createListHouseholdMembersQueryOptions({
      householdId,
    }),
  );

  const getHouseholdDetails = useQuery(
    createGetHouseholdDetailQueryOptions(householdId),
  );

  const listUsersQuery = useQuery(
    createListUsersQueryOptions({
      ids: listHouseholdMembersQuery.data?.content.map(
        (householdMember) => householdMember.userId,
      ),
    }),
  );

  const householdMembers = useMemo(() => {
    return (
      listHouseholdMembersQuery.data?.content.map((householdMember) => {
        const user = listUsersQuery.data?.content.find(
          (user) => user.id === householdMember.userId,
        );
        return {
          id: householdMember.id,
          name:
            user?.firstName || user?.lastName
              ? `${user?.firstName} ${user?.lastName}`
              : "Unknown",
          email: user?.email,
        };
      }) ?? []
    );
  }, [listHouseholdMembersQuery.data?.content, listUsersQuery.data?.content]);

  return (
    <Card>
      <CardHeader className="col-end-2">
        <CardTitle>
          {getHouseholdDetails.data?.name ?? "Household Details"}
        </CardTitle>

        <Button>
          <Link href={`/households/${householdId}/edit`}>Edit</Link>
        </Button>
        <Button
          onClick={() => {
            window.navigator.clipboard.writeText(
              "http://localhost:3000/households/" + householdId + "/join",
            );
            toast({
              title: "Link copied",
              description: "The link has been copied to your clipboard",
            });
          }}
        >
          Invite Member
        </Button>
      </CardHeader>
      <CardContent>
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead className="">Name</TableHead>
              <TableHead className="">Email</TableHead>
              <TableHead>Actions</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {householdMembers.map((householdMember) => (
              <TableRow key={householdMember.id}>
                <TableCell>{householdMember.name}</TableCell>
                <TableCell>{householdMember.email}</TableCell>
                <TableCell>
                  <Button asChild>
                    <Link href={`/households/${householdMember.id}`}>View</Link>
                  </Button>

                  <Button asChild>
                    <Link href={`/households/${householdMember.id}`}>Kick</Link>
                  </Button>
                </TableCell>
                <TableCell></TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </CardContent>
    </Card>
  );
}
