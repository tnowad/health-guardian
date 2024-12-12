"use client";

import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
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
import { Skeleton } from "@/components/ui/skeleton";
import { Home, Plus, Users } from "lucide-react";
import { Badge } from "@/components/ui/badge";

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

  const isLoading =
    listHouseholdsQuery.isLoading || listHouseholdMembersQuery.isLoading;
  const error = listHouseholdsQuery.error || listHouseholdMembersQuery.error;

  if (error) {
    return (
      <Card className="mx-auto">
        <CardHeader>
          <CardTitle className="text-destructive">Error</CardTitle>
        </CardHeader>
        <CardContent>
          <p>
            An error occurred while fetching your households. Please try again
            later.
          </p>
        </CardContent>
      </Card>
    );
  }

  const households = listHouseholdsQuery.data?.content ?? [];

  return (
    <div className="py-8">
      <Card>
        <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-4">
          <div>
            <CardTitle className="text-2xl font-bold">My Households</CardTitle>
            <CardDescription>
              Manage and view your household memberships
            </CardDescription>
          </div>
          <Button asChild>
            <Link href="/households/create">
              <Plus className="mr-2 h-4 w-4" />
              Create Household
            </Link>
          </Button>
        </CardHeader>
        <CardContent>
          {isLoading ? (
            <div className="grid sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
              {[...Array(4)].map((_, index) => (
                <Card key={index}>
                  <CardHeader>
                    <Skeleton className="h-[200px] w-full" />
                  </CardHeader>
                  <CardContent className="space-y-2">
                    <Skeleton className="h-4 w-3/4" />
                    <Skeleton className="h-4 w-1/2" />
                  </CardContent>
                  <CardFooter>
                    <Skeleton className="h-10 w-full" />
                  </CardFooter>
                </Card>
              ))}
            </div>
          ) : households.length === 0 ? (
            <Card className="bg-muted">
              <CardContent className="flex flex-col items-center justify-center py-10">
                <Home className="h-12 w-12 text-muted-foreground mb-4" />
                <h3 className="text-lg font-semibold mb-2">
                  No Households Yet
                </h3>
                <p className="text-muted-foreground text-center mb-4">
                  You haven't joined or created any households. Get started by
                  creating your first household!
                </p>
                <Button asChild>
                  <Link href="/households/create">
                    <Plus className="mr-2 h-4 w-4" />
                    Create Your First Household
                  </Link>
                </Button>
              </CardContent>
            </Card>
          ) : (
            <div className="grid sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
              {households.map((household) => (
                <Card key={household.id} className="flex flex-col">
                  <CardHeader className="p-0">
                    <AspectRatio ratio={16 / 9}>
                      <Image
                        src={
                          household.avatar ??
                          "/placeholder.svg?height=300&width=300"
                        }
                        alt={`${household.name} Avatar`}
                        className="rounded-t-lg object-cover"
                        fill
                        sizes="(max-width: 768px) 100vw, (max-width: 1200px) 50vw, 33vw"
                      />
                    </AspectRatio>
                  </CardHeader>
                  <CardContent className="flex-grow p-4">
                    <CardTitle className="text-xl mb-2">
                      {household.name}
                    </CardTitle>
                    {currentUserInformationQuery.data.userId ===
                      household.headId && (
                      <Badge variant="secondary" className="mb-2">
                        Head of Household
                      </Badge>
                    )}
                    <CardDescription className="flex items-center">
                      <Users className="mr-2 h-4 w-4" />
                      {listHouseholdMembersQuery.data?.content.filter(
                        (member) => member.householdId === household.id,
                      ).length ?? 0}{" "}
                      members
                    </CardDescription>
                  </CardContent>
                  <CardFooter className="p-4 pt-0">
                    <Button asChild className="w-full">
                      <Link href={`/households/${household.id}`}>
                        View Details
                      </Link>
                    </Button>
                  </CardFooter>
                </Card>
              ))}
            </div>
          )}
        </CardContent>
      </Card>
    </div>
  );
}
