"use client";

import { useState } from "react";
import { format, parseISO } from "date-fns";
import { CalendarIcon } from "lucide-react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
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
import { Calendar } from "@/components/ui/calendar";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { useQuery, useSuspenseQuery } from "@tanstack/react-query";
import { createListVisitSummariesQueryOptions } from "@/lib/apis/(visit-summary)/list-visit-summaries.api";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
import {
  visitSummarySchema,
  VisitSummarySchema,
} from "@/lib/schemas/(visit-summary)/visit-summary.schema";

const visitTypeMap = {
  CHECKUP: "Check-up",
  FOLLOW_UP: "Follow-up",
  EMERGENCY: "Emergency",
  CONSULTATION: "Consultation",
  SURGERY: "Surgery",
  THERAPY: "Therapy",
};

const reverseVisitTypeMap = Object.fromEntries(
  Object.entries(visitTypeMap).map(([key, value]) => [value, key])
);

export default function VisitSummariesScreen() {
  //   const [visitSummaries, setVisitSummaries] = useState<VisitSummarySchema[]>(
  //     []
  //   );
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingVisit, setEditingVisit] = useState<VisitSummarySchema | null>(
    null
  );
  const [newVisit, setNewVisit] = useState<Partial<VisitSummarySchema>>({
    visitDate: "",
    visitType: "CHECKUP",
    summary: "",
    notes: "",
  });

  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions()
  );

  const listVisitSummariesQuery = useQuery(
    createListVisitSummariesQueryOptions({
      userId: currentUserInformationQuery.data?.userId,
    })
  );

  const visitSummaries = listVisitSummariesQuery.data?.content ?? [];

  const handleEditVisit = (visit: VisitSummarySchema) => {
    setEditingVisit(visit);
    setNewVisit({
      ...visit,
      visitDate: visit.visitDate,
    });
    setIsModalOpen(true);
  };

  const handleAddVisit = () => {
    setEditingVisit(null);
    setNewVisit({
      visitDate: "",
      visitType: "CHECKUP",
      summary: "",
      notes: "",
    });
    setIsModalOpen(true);
  };

  const handleSaveVisit = () => {
    const validatedData = visitSummarySchema.safeParse(newVisit);
    if (!validatedData.success) {
      alert("Invalid data");
      return;
    }

    if (editingVisit) {
      setVisitSummaries(
        visitSummaries.map((visit) =>
          visit.id === editingVisit.id
            ? { ...visit, ...(validatedData.data as VisitSummarySchema) }
            : visit
        )
      );
    } else {
      setVisitSummaries([
        ...visitSummaries,
        {
          id: Date.now().toString(),
          ...(validatedData.data as VisitSummarySchema),
        },
      ]);
    }
    setIsModalOpen(false);
  };

  return (
    <div className="container mx-auto p-4">
      <Card>
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-2xl font-bold">Visit Summaries</CardTitle>
          <Button onClick={handleAddVisit}>Add Visit Summary</Button>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Date</TableHead>
                <TableHead>Type</TableHead>
                <TableHead>Summary</TableHead>
                <TableHead>Notes</TableHead>
                <TableHead>Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {visitSummaries.map((visit) => (
                <TableRow key={visit.id}>
                  <TableCell>
                    {format(parseISO(visit.visitDate), "PPP")}
                  </TableCell>
                  <TableCell>{visitTypeMap[visit.visitType]}</TableCell>
                  <TableCell>{visit.summary}</TableCell>
                  <TableCell>{visit.notes}</TableCell>
                  <TableCell>
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={() => handleEditVisit(visit)}
                    >
                      Edit
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>

          <Dialog open={isModalOpen} onOpenChange={setIsModalOpen}>
            <DialogContent>
              <DialogHeader>
                <DialogTitle>
                  {editingVisit ? "Edit Visit Summary" : "Add Visit Summary"}
                </DialogTitle>
              </DialogHeader>
              <div className="grid gap-4 py-4">
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label className="text-right">Date</Label>
                  <Popover>
                    <PopoverTrigger asChild>
                      <Button
                        variant={"outline"}
                        className={`col-span-3 justify-start text-left font-normal ${
                          !newVisit.visitDate && "text-muted-foreground"
                        }`}
                      >
                        <CalendarIcon className="mr-2 h-4 w-4" />
                        {newVisit.visitDate ? (
                          format(parseISO(newVisit.visitDate), "PPP")
                        ) : (
                          <span>Pick a date</span>
                        )}
                      </Button>
                    </PopoverTrigger>
                    <PopoverContent className="w-auto p-0">
                      <Calendar
                        mode="single"
                        selected={
                          newVisit.visitDate
                            ? parseISO(newVisit.visitDate)
                            : undefined
                        }
                        onSelect={(date) =>
                          setNewVisit({
                            ...newVisit,
                            visitDate: date?.toISOString() || "",
                          })
                        }
                        initialFocus
                      />
                    </PopoverContent>
                  </Popover>
                </div>
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="type" className="text-right">
                    Type
                  </Label>
                  <Select
                    value={visitTypeMap[newVisit.visitType]}
                    onValueChange={(value) =>
                      setNewVisit({
                        ...newVisit,
                        visitType: reverseVisitTypeMap[value] || "CHECKUP",
                      })
                    }
                  >
                    <SelectTrigger className="col-span-3">
                      <SelectValue placeholder="Select visit type" />
                    </SelectTrigger>
                    <SelectContent>
                      {Object.values(visitTypeMap).map((type) => (
                        <SelectItem key={type} value={type}>
                          {type}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="summary" className="text-right">
                    Summary
                  </Label>
                  <Textarea
                    id="summary"
                    value={newVisit.summary}
                    onChange={(e) =>
                      setNewVisit({ ...newVisit, summary: e.target.value })
                    }
                    className="col-span-3"
                  />
                </div>
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="notes" className="text-right">
                    Notes
                  </Label>
                  <Textarea
                    id="notes"
                    value={newVisit.notes}
                    onChange={(e) =>
                      setNewVisit({ ...newVisit, notes: e.target.value })
                    }
                    className="col-span-3"
                  />
                </div>
              </div>
              <div className="flex justify-end space-x-2">
                <Button variant="outline" onClick={() => setIsModalOpen(false)}>
                  Cancel
                </Button>
                <Button onClick={handleSaveVisit}>Save</Button>
              </div>
            </DialogContent>
          </Dialog>
        </CardContent>
      </Card>
    </div>
  );
}
