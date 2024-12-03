"use client";

import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Input } from "@/components/ui/input";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";

interface SideEffectReport {
  medication: string;
  sideEffect: string;
  reportCount: number;
}

const mockSideEffectReports: SideEffectReport[] = [
  { medication: "Lisinopril", sideEffect: "Dizziness", reportCount: 3 },
  { medication: "Lisinopril", sideEffect: "Cough", reportCount: 5 },
  { medication: "Metformin", sideEffect: "Nausea", reportCount: 2 },
  { medication: "Metformin", sideEffect: "Diarrhea", reportCount: 4 },
];

interface AggregatedSideEffectsProps {
  dateRange: { start: string; end: string };
  setDateRange: (range: { start: string; end: string }) => void;
  selectedMedication: string;
  setSelectedMedication: (medication: string) => void;
}

export default function AggregatedSideEffects({
  dateRange,
  setDateRange,
  selectedMedication,
  setSelectedMedication,
}: AggregatedSideEffectsProps) {
  const medications = Array.from(
    new Set(mockSideEffectReports.map((report) => report.medication)),
  );

  const filteredReports = mockSideEffectReports.filter(
    (report) => !selectedMedication || report.medication === selectedMedication,
  );

  return (
    <Card>
      <CardHeader>
        <CardTitle>Aggregated Side Effects</CardTitle>
      </CardHeader>
      <CardContent>
        <div className="flex space-x-4 mb-4">
          <div className="flex-1">
            <label
              htmlFor="start-date"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              Start Date
            </label>
            <Input
              type="date"
              id="start-date"
              value={dateRange.start}
              onChange={(e) =>
                setDateRange({ ...dateRange, start: e.target.value })
              }
            />
          </div>
          <div className="flex-1">
            <label
              htmlFor="end-date"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              End Date
            </label>
            <Input
              type="date"
              id="end-date"
              value={dateRange.end}
              onChange={(e) =>
                setDateRange({ ...dateRange, end: e.target.value })
              }
            />
          </div>
          <div className="flex-1">
            <label
              htmlFor="medication"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              Medication
            </label>
            <Select
              value={selectedMedication}
              onValueChange={setSelectedMedication}
            >
              <SelectTrigger id="medication">
                <SelectValue placeholder="All Medications" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="all-medications">All Medications</SelectItem>
                {medications.map((medication) => (
                  <SelectItem key={medication} value={medication}>
                    {medication}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
        </div>
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>Medication</TableHead>
              <TableHead>Side Effect</TableHead>
              <TableHead>Report Count</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {filteredReports.map((report, index) => (
              <TableRow key={index}>
                <TableCell>{report.medication}</TableCell>
                <TableCell>{report.sideEffect}</TableCell>
                <TableCell>{report.reportCount}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </CardContent>
    </Card>
  );
}
