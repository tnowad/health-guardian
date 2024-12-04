"use client";

import { useState } from "react";
import { format } from "date-fns";
import { CalendarIcon } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Calendar } from "@/components/ui/calendar";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { useQuery, useSuspenseQuery } from "@tanstack/react-query";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
import { createListPatientLogsQueryOptions } from "@/lib/apis/list-patient-logs.api";

interface PatientLog {
  id: string;
  actionType: string;
  description: string;
  date: Date;
}

const actionTypes = [
  "Medication",
  "Vital Signs",
  "Lab Results",
  "Appointments",
  "Notes",
];

const initialLogs: PatientLog[] = [
  {
    id: "1",
    actionType: "Medication",
    description: "Administered 500mg Paracetamol",
    date: new Date(2023, 5, 1, 9, 30),
  },
  {
    id: "2",
    actionType: "Vital Signs",
    description: "Blood Pressure: 120/80 mmHg",
    date: new Date(2023, 5, 2, 10, 15),
  },
  {
    id: "3",
    actionType: "Lab Results",
    description: "Blood Test: Normal",
    date: new Date(2023, 5, 3, 14, 0),
  },
  {
    id: "4",
    actionType: "Appointments",
    description: "Scheduled follow-up for next week",
    date: new Date(2023, 5, 4, 11, 45),
  },
  {
    id: "5",
    actionType: "Notes",
    description: "Patient reported feeling better",
    date: new Date(2023, 5, 5, 16, 30),
  },
];

export default function PatientLogsScreen() {
  const getCurrentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions(),
  );

  const listPatientLogsQuery = useQuery(
    createListPatientLogsQueryOptions({
      patientId: "d6b53a8e-d4e8-4198-b162-024b9db223e5",
    }),
  );

  const [logs, setLogs] = useState<PatientLog[]>(initialLogs);
  const [selectedActionType, setSelectedActionType] = useState<string | null>(
    null,
  );
  const [dateRange, setDateRange] = useState<{
    from: Date | undefined;
    to: Date | undefined;
  }>({
    from: undefined,
    to: undefined,
  });

  console.log(listPatientLogsQuery.data);

  const filteredLogs = logs.filter((log) => {
    const matchesActionType =
      !selectedActionType || log.actionType === selectedActionType;
    const matchesDateRange =
      (!dateRange.from || log.date >= dateRange.from) &&
      (!dateRange.to || log.date <= dateRange.to);
    return matchesActionType && matchesDateRange;
  });

  return (
    <div className="container mx-auto p-4">
      <Card>
        <CardHeader>
          <CardTitle className="text-2xl font-bold">Patient Logs</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="flex flex-col md:flex-row justify-between mb-4 space-y-2 md:space-y-0 md:space-x-2">
            <Select onValueChange={(value) => setSelectedActionType(value)}>
              <SelectTrigger className="w-full md:w-[180px]">
                <SelectValue placeholder="Filter by action" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="all-actions">All Actions</SelectItem>
                {actionTypes.map((type) => (
                  <SelectItem key={type} value={type}>
                    {type}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
            <Popover>
              <PopoverTrigger asChild>
                <Button
                  variant={"outline"}
                  className={`w-full md:w-[300px] justify-start text-left font-normal ${
                    !dateRange.from && !dateRange.to && "text-muted-foreground"
                  }`}
                >
                  <CalendarIcon className="mr-2 h-4 w-4" />
                  {dateRange.from ? (
                    dateRange.to ? (
                      <>
                        {format(dateRange.from, "LLL dd, y")} -{" "}
                        {format(dateRange.to, "LLL dd, y")}
                      </>
                    ) : (
                      format(dateRange.from, "LLL dd, y")
                    )
                  ) : (
                    <span>Pick a date range</span>
                  )}
                </Button>
              </PopoverTrigger>
              <PopoverContent className="w-auto p-0" align="start">
                <Calendar
                  initialFocus
                  mode="range"
                  defaultMonth={dateRange.from}
                  selected={dateRange}
                  onSelect={setDateRange}
                  numberOfMonths={2}
                />
              </PopoverContent>
            </Popover>
          </div>
          <div className="space-y-4">
            {listPatientLogsQuery.data?.content.map((log) => (
              <div
                key={log.id}
                className="flex flex-col md:flex-row justify-between p-2 border rounded"
              >
                <div>
                  <span className="font-medium">{log.logType}</span>
                  <p className="text-sm text-gray-600">{log.description}</p>
                </div>
                <div className="text-sm text-gray-500 mt-2 md:mt-0"></div>
              </div>
            ))}
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
