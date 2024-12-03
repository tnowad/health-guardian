"use client";

import { useState, useMemo } from "react";
import { format } from "date-fns";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
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
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Badge } from "@/components/ui/badge";

export interface SideEffectReport {
  id: string;
  patientName: string;
  sideEffect: string;
  severity: "Mild" | "Moderate" | "Severe";
  date: Date;
  status: "New" | "In Progress" | "Resolved";
}

export const sideEffectReportsData: SideEffectReport[] = [
  {
    id: "1",
    patientName: "John Doe",
    sideEffect: "Nausea",
    severity: "Mild",
    date: new Date(2023, 5, 1),
    status: "New",
  },
  {
    id: "2",
    patientName: "Jane Smith",
    sideEffect: "Headache",
    severity: "Moderate",
    date: new Date(2023, 5, 2),
    status: "In Progress",
  },
  {
    id: "3",
    patientName: "Bob Johnson",
    sideEffect: "Dizziness",
    severity: "Severe",
    date: new Date(2023, 5, 3),
    status: "Resolved",
  },
  {
    id: "4",
    patientName: "Alice Brown",
    sideEffect: "Rash",
    severity: "Mild",
    date: new Date(2023, 5, 4),
    status: "New",
  },
  {
    id: "5",
    patientName: "Charlie Wilson",
    sideEffect: "Fatigue",
    severity: "Moderate",
    date: new Date(2023, 5, 5),
    status: "In Progress",
  },
  {
    id: "6",
    patientName: "Eva Davis",
    sideEffect: "Nausea",
    severity: "Severe",
    date: new Date(2023, 5, 6),
    status: "New",
  },
  {
    id: "7",
    patientName: "Frank Miller",
    sideEffect: "Headache",
    severity: "Mild",
    date: new Date(2023, 5, 7),
    status: "Resolved",
  },
  {
    id: "8",
    patientName: "Grace Taylor",
    sideEffect: "Dizziness",
    severity: "Moderate",
    date: new Date(2023, 5, 8),
    status: "In Progress",
  },
  {
    id: "9",
    patientName: "Henry Clark",
    sideEffect: "Rash",
    severity: "Severe",
    date: new Date(2023, 5, 9),
    status: "New",
  },
  {
    id: "10",
    patientName: "Ivy Anderson",
    sideEffect: "Fatigue",
    severity: "Mild",
    date: new Date(2023, 5, 10),
    status: "Resolved",
  },
];

export const severityOptions = ["Mild", "Moderate", "Severe"];
export const sideEffectTypes = Array.from(
  new Set(sideEffectReportsData.map((report) => report.sideEffect))
);

export default function SideEffectReportsScreen() {
  const [selectedSeverity, setSelectedSeverity] = useState<string | null>(null);
  const [selectedSideEffect, setSelectedSideEffect] = useState<string | null>(
    null
  );
  const [dateRange, setDateRange] = useState<{
    from: Date | undefined;
    to: Date | undefined;
  }>({
    from: undefined,
    to: undefined,
  });

  const filteredReports = useMemo(() => {
    return sideEffectReportsData.filter((report) => {
      const matchesSeverity =
        !selectedSeverity || report.severity === selectedSeverity;
      const matchesSideEffect =
        !selectedSideEffect || report.sideEffect === selectedSideEffect;
      const matchesDateRange =
        (!dateRange.from || report.date >= dateRange.from) &&
        (!dateRange.to || report.date <= dateRange.to);
      return matchesSeverity && matchesSideEffect && matchesDateRange;
    });
  }, [selectedSeverity, selectedSideEffect, dateRange]);

  const getSeverityColor = (severity: SideEffectReport["severity"]) => {
    switch (severity) {
      case "Mild":
        return "bg-green-100 text-green-800";
      case "Moderate":
        return "bg-yellow-100 text-yellow-800";
      case "Severe":
        return "bg-red-100 text-red-800";
      default:
        return "bg-gray-100 text-gray-800";
    }
  };

  const getStatusColor = (status: SideEffectReport["status"]) => {
    switch (status) {
      case "New":
        return "bg-blue-100 text-blue-800";
      case "In Progress":
        return "bg-purple-100 text-purple-800";
      case "Resolved":
        return "bg-green-100 text-green-800";
      default:
        return "bg-gray-100 text-gray-800";
    }
  };

  return (
    <div className="container mx-auto p-4">
      <Card>
        <CardHeader>
          <CardTitle className="text-2xl font-bold">
            Side Effect Reports
          </CardTitle>
        </CardHeader>
        <CardContent>
          <div className="flex flex-col md:flex-row justify-between mb-4 space-y-2 md:space-y-0 md:space-x-2">
            <Select onValueChange={(value) => setSelectedSeverity(value)}>
              <SelectTrigger className="w-full md:w-[180px]">
                <SelectValue placeholder="Filter by severity" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="all-severities">All Severities</SelectItem>
                {severityOptions.map((severity) => (
                  <SelectItem key={severity} value={severity}>
                    {severity}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
            <Select onValueChange={(value) => setSelectedSideEffect(value)}>
              <SelectTrigger className="w-full md:w-[180px]">
                <SelectValue placeholder="Filter by side effect" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="all-side-effects">
                  All Side Effects
                </SelectItem>
                {sideEffectTypes.map((effect) => (
                  <SelectItem key={effect} value={effect}>
                    {effect}
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

          <div className="overflow-x-auto">
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Patient Name</TableHead>
                  <TableHead>Side Effect</TableHead>
                  <TableHead>Severity</TableHead>
                  <TableHead>Date</TableHead>
                  <TableHead>Status</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {filteredReports.map((report) => (
                  <TableRow key={report.id}>
                    <TableCell>{report.patientName}</TableCell>
                    <TableCell>{report.sideEffect}</TableCell>
                    <TableCell>
                      <Badge className={getSeverityColor(report.severity)}>
                        {report.severity}
                      </Badge>
                    </TableCell>
                    <TableCell>{format(report.date, "MMM d, yyyy")}</TableCell>
                    <TableCell>
                      <Badge className={getStatusColor(report.status)}>
                        {report.status}
                      </Badge>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
