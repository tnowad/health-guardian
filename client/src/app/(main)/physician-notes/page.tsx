"use client";

import { useState } from "react";
import { format } from "date-fns";
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
import { Calendar } from "@/components/ui/calendar";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { CalendarIcon } from "lucide-react";
import { useQuery, useSuspenseQuery } from "@tanstack/react-query";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
import { createListPhysicianNotesQueryOptions } from "@/lib/apis/(physician-note)/physician-note.api";
import { PhysicianNoteSchema } from "@/lib/schemas/(physician-note)/physician-note.schema";

export interface PhysicianNote {
  id: string;
  date: Date;
  note: string;
}

export default function PhysicianNotesScreen() {
  //   const [physicianNotes, setPhysicianNotes] =
  //     useState<PhysicianNote[]>(physicianNotesData);

  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions(),
  );

  const listPhysicianNotesQuery = useQuery(
    createListPhysicianNotesQueryOptions({
      userId: currentUserInformationQuery.data?.userId,
    }),
  );

  const physicianNotes = listPhysicianNotesQuery.data?.content ?? [];

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingNote, setEditingNote] = useState<PhysicianNoteSchema | null>(
    null,
  );
  const [newNote, setNewNote] = useState<Partial<PhysicianNoteSchema>>({
    id: "",
    date: new Date(),
    note: "",
  });
  const [filterStartDate, setFilterStartDate] = useState<Date | undefined>(
    undefined,
  );
  const [filterEndDate, setFilterEndDate] = useState<Date | undefined>(
    undefined,
  );
  const [filterNote, setFilterNote] = useState("");
  const [filteredNotes, setFilteredNotes] =
    useState<PhysicianNoteSchema[]>(physicianNotes);

  const handleEditNote = (note: PhysicianNoteSchema) => {
    setEditingNote(note);
    setNewNote(note);
    setIsModalOpen(true);
  };

  const handleAddNote = () => {
    setEditingNote(null);
    setNewNote({
      id: `PN${String(physicianNotes.length + 1).padStart(3, "0")}`,
      date: new Date(),
      note: "",
    });
    setIsModalOpen(true);
  };

  const handleSaveNote = () => {
    if (newNote.id && newNote.date && newNote.note) {
      if (editingNote) {
        setPhysicianNotes(
          physicianNotes.map((note) =>
            note.id === editingNote.id
              ? { ...note, ...(newNote as PhysicianNoteSchema) }
              : note,
          ),
        );
      } else {
        setPhysicianNotes([...physicianNotes, newNote as PhysicianNoteSchema]);
      }
      setIsModalOpen(false);
      applyFilter();
    }
  };

  const handleRemoveNote = (id: string) => {
    setPhysicianNotes(physicianNotes.filter((note) => note.id !== id));
    applyFilter();
  };

  const applyFilter = () => {
    const filtered = physicianNotes.filter(
      (note) =>
        (!filterStartDate || note.date >= filterStartDate) &&
        (!filterEndDate || note.date <= filterEndDate) &&
        note.note.toLowerCase().includes(filterNote.toLowerCase()),
    );
    setFilteredNotes(filtered);
  };

  return (
    <div className="container mx-auto p-4">
      <Card>
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-2xl font-bold">Physician Notes</CardTitle>
          <Button onClick={handleAddNote}>Add Physician Note</Button>
        </CardHeader>
        <CardContent>
          <div className="flex flex-col md:flex-row gap-2 mb-4">
            <div className="flex-1">
              <Label htmlFor="startDate">Start Date</Label>
              <Popover>
                <PopoverTrigger asChild>
                  <Button
                    variant={"outline"}
                    className={`w-full justify-start text-left font-normal ${
                      !filterStartDate && "text-muted-foreground"
                    }`}
                  >
                    <CalendarIcon className="mr-2 h-4 w-4" />
                    {filterStartDate ? (
                      format(filterStartDate, "PPP")
                    ) : (
                      <span>Pick a date</span>
                    )}
                  </Button>
                </PopoverTrigger>
                <PopoverContent className="w-auto p-0">
                  <Calendar
                    mode="single"
                    selected={filterStartDate}
                    onSelect={setFilterStartDate}
                    initialFocus
                  />
                </PopoverContent>
              </Popover>
            </div>
            <div className="flex-1">
              <Label htmlFor="endDate">End Date</Label>
              <Popover>
                <PopoverTrigger asChild>
                  <Button
                    variant={"outline"}
                    className={`w-full justify-start text-left font-normal ${
                      !filterEndDate && "text-muted-foreground"
                    }`}
                  >
                    <CalendarIcon className="mr-2 h-4 w-4" />
                    {filterEndDate ? (
                      format(filterEndDate, "PPP")
                    ) : (
                      <span>Pick a date</span>
                    )}
                  </Button>
                </PopoverTrigger>
                <PopoverContent className="w-auto p-0">
                  <Calendar
                    mode="single"
                    selected={filterEndDate}
                    onSelect={setFilterEndDate}
                    initialFocus
                  />
                </PopoverContent>
              </Popover>
            </div>
            <div className="flex-1">
              <Label htmlFor="noteFilter">Note</Label>
              <Input
                id="noteFilter"
                placeholder="Filter by note content"
                value={filterNote}
                onChange={(e) => setFilterNote(e.target.value)}
              />
            </div>
            <div className="flex items-end">
              <Button onClick={applyFilter}>Find</Button>
            </div>
          </div>

          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>ID</TableHead>
                <TableHead>Date</TableHead>
                <TableHead>Note</TableHead>
                <TableHead>Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {physicianNotes.map((note) => (
                <TableRow key={note.id}>
                  <TableCell>{note.id}</TableCell>
                  <TableCell>{format(note.date, "PPP")}</TableCell>
                  <TableCell>{note.note}</TableCell>
                  <TableCell>
                    <div className="flex space-x-2">
                      <Button
                        variant="outline"
                        size="sm"
                        onClick={() => handleEditNote(note)}
                      >
                        Edit
                      </Button>
                      <Button
                        variant="destructive"
                        size="sm"
                        onClick={() => handleRemoveNote(note.id)}
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
                  {editingNote ? "Edit Physician Note" : "Add Physician Note"}
                </DialogTitle>
              </DialogHeader>
              <div className="grid gap-4 py-4">
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="id" className="text-right">
                    ID
                  </Label>
                  <Input
                    id="id"
                    value={newNote.id}
                    className="col-span-3"
                    readOnly
                  />
                </div>
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="date" className="text-right">
                    Date
                  </Label>
                  <Popover>
                    <PopoverTrigger asChild>
                      <Button
                        variant={"outline"}
                        className={`col-span-3 justify-start text-left font-normal ${
                          !newNote.date && "text-muted-foreground"
                        }`}
                      >
                        <CalendarIcon className="mr-2 h-4 w-4" />
                        {newNote.date ? (
                          format(newNote.date, "PPP")
                        ) : (
                          <span>Pick a date</span>
                        )}
                      </Button>
                    </PopoverTrigger>
                    <PopoverContent className="w-auto p-0">
                      <Calendar
                        mode="single"
                        selected={newNote.date}
                        onSelect={(date) =>
                          setNewNote({ ...newNote, date: date || new Date() })
                        }
                        initialFocus
                      />
                    </PopoverContent>
                  </Popover>
                </div>
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="note" className="text-right">
                    Note
                  </Label>
                  <Textarea
                    id="note"
                    value={newNote.note}
                    onChange={(e) =>
                      setNewNote({ ...newNote, note: e.target.value })
                    }
                    className="col-span-3"
                  />
                </div>
              </div>
              <div className="flex justify-end space-x-2">
                <Button variant="outline" onClick={() => setIsModalOpen(false)}>
                  Cancel
                </Button>
                <Button onClick={handleSaveNote}>Save</Button>
              </div>
            </DialogContent>
          </Dialog>
        </CardContent>
      </Card>
    </div>
  );
}
