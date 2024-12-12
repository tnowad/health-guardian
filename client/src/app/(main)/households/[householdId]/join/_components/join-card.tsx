"use client";

import { useQuery, useSuspenseQuery } from "@tanstack/react-query";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
import { useJoinHouseholdMutation } from "@/lib/apis/join-household.api";
import { Button } from "@/components/ui/button";

export function JoinCard({ householdId }: { householdId: string }) {
  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions(),
  );

  const joinHouseholdMutation = useJoinHouseholdMutation();

  const handleJoinHousehold = () =>
    joinHouseholdMutation.mutate({
      householdId,
      userId: currentUserInformationQuery.data?.userId,
    });

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-2xl font-bold mb-4">Join Household</h1>
      <p className="mb-4">You've been invited to join the household</p>
      <div className="space-y-4">
        <p>Do you want to join</p>
        <div className="space-x-4">
          <Button className="btn btn-primary" onClick={handleJoinHousehold}>
            Yes, Join Household
          </Button>
          <Button variant={"secondary"}>
            <a href="/households">No, Decline Invitation</a>
          </Button>
        </div>
      </div>
    </div>
  );
}
