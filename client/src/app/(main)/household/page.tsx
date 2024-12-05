"use client";

import { useState } from "react";
import { Plus, Trash2 } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { createListHouseholdMembersQueryOptions } from "@/lib/apis/list-household-members.api";
import { createListHouseholdsQueryOptions } from "@/lib/apis/list-households.api";
import { useQuery, useSuspenseQuery } from "@tanstack/react-query";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";

interface HouseholdMember {
  id: string;
  name: string;
  relationship: string;
}

export default function HouseholdScreen() {
  const getCurrentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions(),
  );

  const listHouseholdsQuery = useQuery(
    createListHouseholdsQueryOptions({
      headId: getCurrentUserInformationQuery.data?.patient?.id,
    }),
  );

  const listHouseholdMembersQuery = useQuery(
    createListHouseholdMembersQueryOptions(
      {
        householdId: listHouseholdsQuery.data?.items[0]?.id,
      },
      {},
    ),
  );

  const [members, setMembers] = useState<HouseholdMember[]>([]);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [newMemberName, setNewMemberName] = useState("");
  const [newMemberRelationship, setNewMemberRelationship] = useState("");

  const addMember = () => {
    if (newMemberName && newMemberRelationship) {
      setMembers([
        ...members,
        {
          id: Date.now().toString(),
          name: newMemberName,
          relationship: newMemberRelationship,
        },
      ]);
      setNewMemberName("");
      setNewMemberRelationship("");
      setIsAddModalOpen(false);
    }
  };

  const removeMember = (id: string) => {
    setMembers(members.filter((member) => member.id !== id));
  };

  return (
    <div className="container mx-auto p-4">
      <Card>
        <CardHeader>
          <CardTitle className="text-2xl font-bold">Household</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="space-y-4">
            {members.map((member) => (
              <div
                key={member.id}
                className="flex items-center justify-between p-2 border rounded"
              >
                <div>
                  <span className="font-medium">{member.name}</span>
                  <span className="ml-2 text-sm text-gray-500">
                    {member.relationship}
                  </span>
                </div>
                <Button
                  variant="ghost"
                  size="icon"
                  onClick={() => removeMember(member.id)}
                >
                  <Trash2 className="h-4 w-4" />
                </Button>
              </div>
            ))}
          </div>
          <Dialog open={isAddModalOpen} onOpenChange={setIsAddModalOpen}>
            <DialogTrigger asChild>
              <Button className="mt-4">
                <Plus className="mr-2 h-4 w-4" /> Add Member
              </Button>
            </DialogTrigger>
            <DialogContent>
              <DialogHeader>
                <DialogTitle>Add Household Member</DialogTitle>
              </DialogHeader>
              <div className="grid gap-4 py-4">
                <Input
                  placeholder="Name"
                  value={newMemberName}
                  onChange={(e) => setNewMemberName(e.target.value)}
                />
                <Select
                  value={newMemberRelationship}
                  onValueChange={setNewMemberRelationship}
                >
                  <SelectTrigger>
                    <SelectValue placeholder="Relationship" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="spouse">Spouse</SelectItem>
                    <SelectItem value="child">Child</SelectItem>
                    <SelectItem value="parent">Parent</SelectItem>
                    <SelectItem value="sibling">Sibling</SelectItem>
                    <SelectItem value="other">Other</SelectItem>
                  </SelectContent>
                </Select>
                <div className="flex justify-end space-x-2">
                  <Button
                    variant="outline"
                    onClick={() => setIsAddModalOpen(false)}
                  >
                    Cancel
                  </Button>
                  <Button onClick={addMember}>Add Member</Button>
                </div>
              </div>
            </DialogContent>
          </Dialog>
        </CardContent>
      </Card>
    </div>
  );
}
