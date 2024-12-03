"use client";

import { useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";

interface Appointment {
  id: string;
  date: string;
  doctor: string;
  reason: string;
  status: string;
  notes?: string;
}

const mockPastAppointments: Appointment[] = [
  {
    id: "3",
    date: "2023-05-10 11:00 AM",
    doctor: "Dr. Brown",
    reason: "Flu Symptoms",
    status: "Completed",
    notes: "Prescribed antibiotics and recommended rest for a week.",
  },
  {
    id: "4",
    date: "2023-04-22 3:00 PM",
    doctor: "Dr. Davis",
    reason: "Routine Checkup",
    status: "Completed",
    notes:
      "All vitals normal. Recommended to maintain current diet and exercise routine.",
  },
];

export default function PastAppointments() {
  const [expandedAppointment, setExpandedAppointment] = useState<string | null>(
    null,
  );

  const toggleExpand = (id: string) => {
    setExpandedAppointment(expandedAppointment === id ? null : id);
  };

  return (
    <Card>
      <CardHeader>
        <CardTitle>Past Appointments</CardTitle>
      </CardHeader>
      <CardContent>
        <ul className="space-y-4">
          {mockPastAppointments.map((appointment) => (
            <li key={appointment.id} className="p-4 bg-gray-100 rounded-lg">
              <div className="flex justify-between items-center">
                <div>
                  <p className="font-semibold">{appointment.date}</p>
                  <p>{appointment.doctor}</p>
                  <p className="text-sm text-gray-600">{appointment.reason}</p>
                  <p className="text-sm font-medium">{appointment.status}</p>
                </div>
                <Button
                  variant="ghost"
                  size="sm"
                  onClick={() => toggleExpand(appointment.id)}
                >
                  {expandedAppointment === appointment.id
                    ? "Hide Details"
                    : "Show Details"}
                </Button>
              </div>
              {expandedAppointment === appointment.id && appointment.notes && (
                <div className="mt-4 p-4 bg-white rounded-md">
                  <h4 className="font-semibold mb-2">Notes:</h4>
                  <p>{appointment.notes}</p>
                </div>
              )}
            </li>
          ))}
        </ul>
      </CardContent>
    </Card>
  );
}
