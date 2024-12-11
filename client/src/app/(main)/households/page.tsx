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

export default function HouseholdScreen() {
  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions(),
  );

  const listHouseholdMembersQuery = useQuery(
    createListHouseholdMembersQueryOptions({
      memberId: currentUserInformationQuery.data?.userId,
      size: 100,
    }),
  );

  const listHouseholdsQuery = useQuery(
    createListHouseholdsQueryOptions({
      ids: listHouseholdMembersQuery.data?.content.map(
        (member) => member.householdId,
      ),
    }),
  );

  if (listHouseholdsQuery.isLoading) {
    return <div>Loading...</div>;
  }

  const households = listHouseholdsQuery.data?.content ?? [];

  return (
    <Card>
      <CardHeader>
        <CardTitle>My Households</CardTitle>
      </CardHeader>
      <CardContent>
        <Button asChild>
          <Link href="/households/create">Create Household</Link>
        </Button>

        <div className="grid sm:grid-cols-2 xl:grid-cols-4 gap-2 mt-2">
          {households.map((household) => (
            <Card key={household.id} className="flex-row">
              <CardHeader>
                <AspectRatio ratio={1}>
                  <Image
                    src={household.avatar ?? "/household-avatar.png"}
                    alt="Household Avatar"
                    width={300}
                    height={300}
                  />
                </AspectRatio>
              </CardHeader>
              <CardContent>
                <CardTitle>{household.name}</CardTitle>
                {currentUserInformationQuery.data.userId ===
                household.headId ? (
                  <CardDescription>Head of Household</CardDescription>
                ) : null}
                <Button asChild className="w-full">
                  <Link href={`/households/${household.id}`}>View</Link>
                </Button>
              </CardContent>
            </Card>
          ))}
        </div>
      </CardContent>
    </Card>
  );
}
