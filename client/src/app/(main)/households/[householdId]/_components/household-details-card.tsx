"use client";

import { useState } from "react";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import Link from "next/link";
import { useQuery, useSuspenseQuery } from "@tanstack/react-query";
import { createListHouseholdMembersQueryOptions } from "@/lib/apis/list-household-members.api";
import { createListUsersQueryOptions } from "@/lib/apis/list-users.api";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { useMemo } from "react";
import { createGetHouseholdDetailQueryOptions } from "@/lib/apis/get-household-detail.api";
import { useToast } from "@/hooks/use-toast";
import { useDeleteHouseholdMutation } from "@/lib/apis/delete-household.api";
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
import { Skeleton } from "@/components/ui/skeleton";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Badge } from "@/components/ui/badge";
import {
  Clipboard,
  Home,
  Pencil,
  Trash,
  UserMinus,
  UserPlus,
} from "lucide-react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { useDeleteHouseholdMemberMutation } from "@/lib/apis/delete-household-member.api";

type HouseholdDetailsCardProps = {
  householdId: string;
};

function DeleteHouseholdButton({ householdId }: { householdId: string }) {
  const { toast } = useToast();
  const deleteHouseholdMutation = useDeleteHouseholdMutation({
    id: householdId,
  });

  const onDelete = () =>
    deleteHouseholdMutation.mutate(undefined, {
      onSuccess() {
        toast({
          title: "Household deleted",
          description: "Your household has been deleted successfully",
        });
      },
    });

  return (
    <AlertDialog>
      <AlertDialogTrigger asChild>
        <Button variant="destructive" size="sm">
          <Trash className="mr-2 h-4 w-4" />
          Delete Household
        </Button>
      </AlertDialogTrigger>
      <AlertDialogContent>
        <AlertDialogHeader>
          <AlertDialogTitle>Are you absolutely sure?</AlertDialogTitle>
          <AlertDialogDescription>
            This action cannot be undone. This will permanently delete your
            household and remove all associated data from our servers.
          </AlertDialogDescription>
        </AlertDialogHeader>
        <AlertDialogFooter>
          <AlertDialogCancel>Cancel</AlertDialogCancel>
          <AlertDialogAction onClick={onDelete}>Delete</AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}

export function DeleteMemberButton({ memberId }: { memberId: string }) {
  const { toast } = useToast();

  const deleteHouseholdMemberMutation = useDeleteHouseholdMemberMutation({
    id: memberId,
  });

  const onDeleteHouseholdMember = () =>
    deleteHouseholdMemberMutation.mutate(undefined, {
      onSuccess() {
        toast({
          title: "Member removed",
          description: "The household member has been removed successfully",
        });
      },
    });

  return (
    <DropdownMenuItem
      className="text-destructive"
      onClick={() => onDeleteHouseholdMember()}
    >
      <UserMinus className="mr-2 h-4 w-4" />
      Remove from Household
    </DropdownMenuItem>
  );
}

export function HouseholdDetailsCard({
  householdId,
}: HouseholdDetailsCardProps) {
  const { toast } = useToast();
  const [isInviteLinkCopied, setIsInviteLinkCopied] = useState(false);

  const getHouseholdDetails = useQuery(
    createGetHouseholdDetailQueryOptions(householdId)
  );

  const listHouseholdMembersQuery = useQuery(
    createListHouseholdMembersQueryOptions({
      householdId,
    })
  );

  const listUsersQuery = useQuery(
    createListUsersQueryOptions({
      ids: listHouseholdMembersQuery.data?.content.map(
        (householdMember) => householdMember.userId
      ),
    })
  );

  const householdMembers = useMemo(() => {
    return (
      listHouseholdMembersQuery.data?.content.map((householdMember) => {
        const user = listUsersQuery.data?.content.find(
          (user) => user.id === householdMember.userId
        );
        return {
          id: householdMember.id,
          name:
            user?.firstName || user?.lastName
              ? `${user?.firstName} ${user?.lastName}`
              : "Unknown",
          email: user?.email,
          avatar: user?.avatar,
          userId: user?.id,
        };
      }) ?? []
    );
  }, [listHouseholdMembersQuery.data?.content, listUsersQuery.data?.content]);

  const copyInviteLink = () => {
    const inviteLink = `http://localhost:3000/households/${householdId}/join`;
    window.navigator.clipboard.writeText(inviteLink);
    setIsInviteLinkCopied(true);
    toast({
      title: "Invite link copied",
      description: "The invite link has been copied to your clipboard",
    });
    setTimeout(() => setIsInviteLinkCopied(false), 3000);
  };

  return (
    <Card className="w-full mx-auto">
      <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
        <div className="space-y-1">
          <CardTitle className="text-2xl">
            {getHouseholdDetails.data?.name ?? (
              <Skeleton className="h-8 w-[200px]" />
            )}
          </CardTitle>
          <CardDescription>
            Manage your household members and settings
          </CardDescription>
        </div>
        <div className="flex space-x-2">
          <Button asChild size="sm">
            <Link href={`/households/${householdId}/edit`}>
              <Pencil className="mr-2 h-4 w-4" />
              Edit
            </Link>
          </Button>
          <Button
            size="sm"
            variant={isInviteLinkCopied ? "secondary" : "outline"}
            onClick={copyInviteLink}
          >
            {isInviteLinkCopied ? (
              <>
                <Clipboard className="mr-2 h-4 w-4" />
                Copied!
              </>
            ) : (
              <>
                <UserPlus className="mr-2 h-4 w-4" />
                Invite Member
              </>
            )}
          </Button>
          <DeleteHouseholdButton householdId={householdId} />
        </div>
      </CardHeader>
      <CardContent>
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead className="w-[250px]">Member</TableHead>
              <TableHead>Email</TableHead>
              <TableHead className="text-right">Actions</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {householdMembers.map((member) => (
              <TableRow key={member.id}>
                <TableCell className="font-medium">
                  <div className="flex items-center space-x-3">
                    <Avatar>
                      <AvatarImage src={member.avatar} alt={member.name} />
                      <AvatarFallback>{member.name.charAt(0)}</AvatarFallback>
                    </Avatar>
                    <div>
                      <p>{member.name}</p>
                      <Badge variant="outline" className="mt-1">
                        Member
                      </Badge>
                    </div>
                  </div>
                </TableCell>
                <TableCell>{member.email}</TableCell>
                <TableCell className="text-right">
                  <DropdownMenu>
                    <DropdownMenuTrigger asChild>
                      <Button variant="ghost" size="sm">
                        Actions
                      </Button>
                    </DropdownMenuTrigger>
                    <DropdownMenuContent align="end">
                      <DropdownMenuLabel>Member Actions</DropdownMenuLabel>
                      <DropdownMenuItem asChild>
                        <Link
                          href={`/health-management?as=${member.userId}`}
                          className="flex items-center"
                        >
                          <Home className="mr-2 h-4 w-4" />
                          View Profile
                        </Link>
                      </DropdownMenuItem>
                      <DropdownMenuSeparator />
                      <DeleteMemberButton memberId={member.id} />
                    </DropdownMenuContent>
                  </DropdownMenu>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </CardContent>
    </Card>
  );
}
