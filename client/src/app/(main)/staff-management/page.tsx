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
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Badge } from "@/components/ui/badge";

export interface StaffMember {
  id: string;
  name: string;
  role: string;
  specialization: string;
  isActive: boolean;
}

export const staffData: StaffMember[] = [
  {
    id: "1",
    name: "Dr. John Smith",
    role: "Doctor",
    specialization: "Cardiology",
    isActive: true,
  },
  {
    id: "2",
    name: "Nurse Sarah Johnson",
    role: "Nurse",
    specialization: "Emergency Care",
    isActive: true,
  },
  {
    id: "3",
    name: "Dr. Emily Brown",
    role: "Doctor",
    specialization: "Pediatrics",
    isActive: false,
  },
  {
    id: "4",
    name: "Tech Alex Lee",
    role: "Technician",
    specialization: "Radiology",
    isActive: true,
  },
  {
    id: "5",
    name: "Dr. Michael Davis",
    role: "Doctor",
    specialization: "Neurology",
    isActive: true,
  },
];

export const roleOptions = ["Doctor", "Nurse", "Technician", "Administrator"];

export default function StaffManagementScreen() {
  const [staff, setStaff] = useState<StaffMember[]>(staffData);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [editingStaff, setEditingStaff] = useState<StaffMember | null>(null);
  const [editRole, setEditRole] = useState("");
  const [editSpecialization, setEditSpecialization] = useState("");

  const handleEditClick = (staffMember: StaffMember) => {
    setEditingStaff(staffMember);
    setEditRole(staffMember.role);
    setEditSpecialization(staffMember.specialization);
    setIsEditModalOpen(true);
  };

  const handleSaveEdit = () => {
    if (editingStaff) {
      const updatedStaff = staff.map((member) =>
        member.id === editingStaff.id
          ? { ...member, role: editRole, specialization: editSpecialization }
          : member
      );
      setStaff(updatedStaff);
      setIsEditModalOpen(false);
    }
  };

  return (
    <div className="container mx-auto p-4">
      <Card>
        <CardHeader>
          <CardTitle className="text-2xl font-bold">Staff Management</CardTitle>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Name</TableHead>
                <TableHead>Role</TableHead>
                <TableHead>Specialization</TableHead>
                <TableHead>Status</TableHead>
                <TableHead>Action</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {staff.map((member) => (
                <TableRow key={member.id}>
                  <TableCell>{member.name}</TableCell>
                  <TableCell>{member.role}</TableCell>
                  <TableCell>{member.specialization}</TableCell>
                  <TableCell>
                    <Badge variant={member.isActive ? "default" : "secondary"}>
                      {member.isActive ? "Active" : "Inactive"}
                    </Badge>
                  </TableCell>
                  <TableCell>
                    <Button
                      variant="outline"
                      onClick={() => handleEditClick(member)}
                    >
                      Edit Role
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>

          <Dialog open={isEditModalOpen} onOpenChange={setIsEditModalOpen}>
            <DialogContent>
              <DialogHeader>
                <DialogTitle>Edit Staff Role</DialogTitle>
              </DialogHeader>
              <div className="grid gap-4 py-4">
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="role" className="text-right">
                    Role
                  </Label>
                  <Select value={editRole} onValueChange={setEditRole}>
                    <SelectTrigger className="col-span-3">
                      <SelectValue placeholder="Select role" />
                    </SelectTrigger>
                    <SelectContent>
                      {roleOptions.map((role) => (
                        <SelectItem key={role} value={role}>
                          {role}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="specialization" className="text-right">
                    Specialization
                  </Label>
                  <Input
                    id="specialization"
                    value={editSpecialization}
                    onChange={(e) => setEditSpecialization(e.target.value)}
                    className="col-span-3"
                  />
                </div>
              </div>
              <div className="flex justify-end space-x-2">
                <Button
                  variant="outline"
                  onClick={() => setIsEditModalOpen(false)}
                >
                  Cancel
                </Button>
                <Button onClick={handleSaveEdit}>Save</Button>
              </div>
            </DialogContent>
          </Dialog>
        </CardContent>
      </Card>
    </div>
  );
}
