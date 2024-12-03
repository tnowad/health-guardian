"use client";

import { useState, useMemo } from "react";
import { format } from "date-fns";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
  PieChart,
  Pie,
  Cell,
} from "recharts";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
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
import {
  ChartContainer,
  ChartTooltip,
  ChartTooltipContent,
} from "@/components/ui/chart";

export interface SideEffectData {
  id: string;
  medication: string;
  sideEffect: string;
  reportCount: number;
  date: Date;
}

export const sideEffectsData: SideEffectData[] = [
  {
    id: "1",
    medication: "Aspirin",
    sideEffect: "Nausea",
    reportCount: 15,
    date: new Date(2023, 4, 1),
  },
  {
    id: "2",
    medication: "Ibuprofen",
    sideEffect: "Stomach Pain",
    reportCount: 12,
    date: new Date(2023, 4, 5),
  },
  {
    id: "3",
    medication: "Amoxicillin",
    sideEffect: "Diarrhea",
    reportCount: 8,
    date: new Date(2023, 4, 10),
  },
  {
    id: "4",
    medication: "Lisinopril",
    sideEffect: "Dizziness",
    reportCount: 10,
    date: new Date(2023, 4, 15),
  },
  {
    id: "5",
    medication: "Metformin",
    sideEffect: "Nausea",
    reportCount: 14,
    date: new Date(2023, 4, 20),
  },
  {
    id: "6",
    medication: "Aspirin",
    sideEffect: "Headache",
    reportCount: 7,
    date: new Date(2023, 4, 25),
  },
  {
    id: "7",
    medication: "Ibuprofen",
    sideEffect: "Dizziness",
    reportCount: 9,
    date: new Date(2023, 5, 1),
  },
  {
    id: "8",
    medication: "Amoxicillin",
    sideEffect: "Rash",
    reportCount: 6,
    date: new Date(2023, 5, 5),
  },
  {
    id: "9",
    medication: "Lisinopril",
    sideEffect: "Cough",
    reportCount: 11,
    date: new Date(2023, 5, 10),
  },
  {
    id: "10",
    medication: "Metformin",
    sideEffect: "Stomach Pain",
    reportCount: 13,
    date: new Date(2023, 5, 15),
  },
];

export const medications = Array.from(
  new Set(sideEffectsData.map((item) => item.medication))
);

export default function AggregatedSideEffectsScreen() {
  const [selectedMedication, setSelectedMedication] = useState<string | null>(
    null
  );
  const [dateRange, setDateRange] = useState<{
    from: Date | undefined;
    to: Date | undefined;
  }>({
    from: undefined,
    to: undefined,
  });

  const filteredData = useMemo(() => {
    return sideEffectsData.filter((item) => {
      const matchesMedication =
        !selectedMedication || item.medication === selectedMedication;
      const matchesDateRange =
        (!dateRange.from || item.date >= dateRange.from) &&
        (!dateRange.to || item.date <= dateRange.to);
      return matchesMedication && matchesDateRange;
    });
  }, [selectedMedication, dateRange]);

  const topSideEffects = useMemo(() => {
    const effectCounts = filteredData.reduce((acc, item) => {
      acc[item.sideEffect] = (acc[item.sideEffect] || 0) + item.reportCount;
      return acc;
    }, {} as Record<string, number>);
    return Object.entries(effectCounts)
      .sort((a, b) => b[1] - a[1])
      .slice(0, 5)
      .map(([name, value]) => ({ name, value }));
  }, [filteredData]);

  const COLORS = ["#0088FE", "#00C49F", "#FFBB28", "#FF8042", "#8884D8"];

  return (
    <div className="container mx-auto p-4">
      <Card>
        <CardHeader>
          <CardTitle className="text-2xl font-bold">
            Aggregated Side Effects
          </CardTitle>
        </CardHeader>
        <CardContent>
          <div className="flex flex-col md:flex-row justify-between mb-4 space-y-2 md:space-y-0 md:space-x-2">
            <Select onValueChange={(value) => setSelectedMedication(value)}>
              <SelectTrigger className="w-full md:w-[180px]">
                <SelectValue placeholder="Filter by medication" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="all-medications">All Medications</SelectItem>
                {medications.map((med) => (
                  <SelectItem key={med} value={med}>
                    {med}
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

          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Medication</TableHead>
                <TableHead>Side Effect</TableHead>
                <TableHead>Report Count</TableHead>
                <TableHead>Date</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {filteredData.map((item) => (
                <TableRow key={item.id}>
                  <TableCell>{item.medication}</TableCell>
                  <TableCell>{item.sideEffect}</TableCell>
                  <TableCell>{item.reportCount}</TableCell>
                  <TableCell>{format(item.date, "MMM d, yyyy")}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>

          <div className="mt-8 grid md:grid-cols-2 gap-8">
            <Card>
              <CardHeader>
                <CardTitle>Top Reported Side Effects</CardTitle>
              </CardHeader>
              <CardContent>
                <ChartContainer
                  config={{
                    reportCount: {
                      label: "Report Count",
                      color: "hsl(var(--chart-1))",
                    },
                  }}
                  className="h-[300px]"
                >
                  <ResponsiveContainer width="100%" height="100%">
                    <BarChart data={topSideEffects}>
                      <CartesianGrid strokeDasharray="3 3" />
                      <XAxis dataKey="name" />
                      <YAxis />
                      <ChartTooltip content={<ChartTooltipContent />} />
                      <Bar
                        dataKey="value"
                        fill="var(--color-reportCount)"
                        name="Report Count"
                      />
                    </BarChart>
                  </ResponsiveContainer>
                </ChartContainer>
              </CardContent>
            </Card>

            <Card>
              <CardHeader>
                <CardTitle>Side Effects Distribution</CardTitle>
              </CardHeader>
              <CardContent>
                <ResponsiveContainer width="100%" height={300}>
                  <PieChart>
                    <Pie
                      data={topSideEffects}
                      cx="50%"
                      cy="50%"
                      labelLine={false}
                      outerRadius={80}
                      fill="#8884d8"
                      dataKey="value"
                      label={({ name, percent }) =>
                        `${name} ${(percent * 100).toFixed(0)}%`
                      }
                    >
                      {topSideEffects.map((entry, index) => (
                        <Cell
                          key={`cell-${index}`}
                          fill={COLORS[index % COLORS.length]}
                        />
                      ))}
                    </Pie>
                    <Tooltip />
                    <Legend />
                  </PieChart>
                </ResponsiveContainer>
              </CardContent>
            </Card>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
