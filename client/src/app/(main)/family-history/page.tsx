"use client";

import { useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { useQuery, useSuspenseQuery } from "@tanstack/react-query";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
import { createListFamilyHistoriesQueryOptions } from "@/lib/apis/(family-history)/list-family-histories.api";
import { FamilyHistorySchema } from "@/lib/schemas/family-history/family-history.schema";

export interface FamilyHistory {
  id: string;
  relation: string;
  condition: string;
  description: string;
}

export const familyHistoriesData: FamilyHistory[] = [
  {
    id: "1",
    relation: "Mother",
    condition: "Hypertension",
    description:
      "Diagnosed at age 45, managed with medication and lifestyle changes.",
  },
  {
    id: "2",
    relation: "Father",
    condition: "Type 2 Diabetes",
    description: "Diagnosed at age 50, controlled through diet and exercise.",
  },
  {
    id: "3",
    relation: "Maternal Grandmother",
    condition: "Breast Cancer",
    description: "Diagnosed at age 60, underwent successful treatment.",
  },
  {
    id: "4",
    relation: "Paternal Grandfather",
    condition: "Alzheimer's Disease",
    description: "Onset at age 75, progressed over 8 years.",
  },
];

export default function FamilyHistoriesScreen() {
  //   const [familyHistories, setFamilyHistories] =
  //     useState<FamilyHistory[]>(familyHistoriesData);

  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions()
  );

  const listFamiliesQuery = useQuery(
    createListFamilyHistoriesQueryOptions({
      userId: currentUserInformationQuery.data?.userId,
    })
  );

  const familyHistories = listFamiliesQuery.data?.content ?? [];

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingHistory, setEditingHistory] =
    useState<FamilyHistorySchema | null>(null);
  const [newHistory, setNewHistory] = useState<Partial<FamilyHistorySchema>>({
    relation: "",
    condition: "",
    description: "",
  });
  const [filterRelation, setFilterRelation] = useState("");
  const [filteredHistories, setFilteredHistories] =
    useState<FamilyHistorySchema[]>(familyHistories);

  const handleEditHistory = (history: FamilyHistorySchema) => {
    setEditingHistory(history);
    setNewHistory(history);
    setIsModalOpen(true);
  };

  const handleAddHistory = () => {
    setEditingHistory(null);
    setNewHistory({
      relation: "",
      condition: "",
      description: "",
    });
    setIsModalOpen(true);
  };

  const handleSaveHistory = () => {
    if (newHistory.relation && newHistory.condition) {
      if (editingHistory) {
        setFamilyHistories(
          familyHistories.map((history) =>
            history.id === editingHistory.id
              ? { ...history, ...(newHistory as FamilyHistorySchema) }
              : history
          )
        );
      } else {
        setFamilyHistories([
          ...familyHistories,
          { id: Date.now().toString(), ...(newHistory as FamilyHistorySchema) },
        ]);
      }
      setIsModalOpen(false);
      applyFilter();
    }
  };

  const handleRemoveHistory = (id: string) => {
    setFamilyHistories(familyHistories.filter((history) => history.id !== id));
    applyFilter();
  };

  const applyFilter = () => {
    const filtered = familyHistories.filter((history) =>
      history.relation.toLowerCase().includes(filterRelation.toLowerCase())
    );
    setFilteredHistories(filtered);
  };

  return (
    <div className="container mx-auto p-4">
      <Card>
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-2xl font-bold">Family Histories</CardTitle>
          <Button onClick={handleAddHistory}>Add Family History</Button>
        </CardHeader>
        <CardContent>
          <div className="flex gap-2 mb-4">
            <Input
              placeholder="Filter by relation"
              value={filterRelation}
              onChange={(e) => setFilterRelation(e.target.value)}
            />
            <Button onClick={applyFilter}>Find</Button>
          </div>

          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Relation</TableHead>
                <TableHead>Condition</TableHead>
                <TableHead>Description</TableHead>
                <TableHead>Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {familyHistories.map((history) => (
                <TableRow key={history.id}>
                  <TableCell>{history.relation}</TableCell>
                  <TableCell>{history.condition}</TableCell>
                  <TableCell>{history.description}</TableCell>
                  <TableCell>
                    <div className="flex space-x-2">
                      <Button
                        variant="outline"
                        size="sm"
                        onClick={() => handleEditHistory(history)}
                      >
                        Edit
                      </Button>
                      <Button
                        variant="destructive"
                        size="sm"
                        onClick={() => handleRemoveHistory(history.id)}
                      >
                        Remove
                      </Button>
                    </div>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>

          <Dialog open={isModalOpen} onOpenChange={setIsModalOpen}>
            <DialogContent className="sm:max-w-[425px]">
              <DialogHeader>
                <DialogTitle>
                  {editingHistory
                    ? "Edit Family History"
                    : "Add Family History"}
                </DialogTitle>
              </DialogHeader>
              <div className="grid gap-4 py-4">
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="relation" className="text-right">
                    Relation
                  </Label>
                  <Input
                    id="relation"
                    value={newHistory.relation}
                    onChange={(e) =>
                      setNewHistory({ ...newHistory, relation: e.target.value })
                    }
                    className="col-span-3"
                  />
                </div>
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="condition" className="text-right">
                    Condition
                  </Label>
                  <Textarea
                    id="condition"
                    value={newHistory.condition}
                    onChange={(e) =>
                      setNewHistory({
                        ...newHistory,
                        condition: e.target.value,
                      })
                    }
                    className="col-span-3"
                  />
                </div>
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="description" className="text-right">
                    Description
                  </Label>
                  <Textarea
                    id="description"
                    value={newHistory.description}
                    onChange={(e) =>
                      setNewHistory({
                        ...newHistory,
                        description: e.target.value,
                      })
                    }
                    className="col-span-3"
                  />
                </div>
              </div>
              <div className="flex justify-end space-x-2">
                <Button variant="outline" onClick={() => setIsModalOpen(false)}>
                  Cancel
                </Button>
                <Button onClick={handleSaveHistory}>Save</Button>
              </div>
            </DialogContent>
          </Dialog>
        </CardContent>
      </Card>
    </div>
  );
}
