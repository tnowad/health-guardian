"use client";

import { Badge } from "@/components/ui/badge";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";

import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import {
  AtSign,
  Calendar,
  Mail,
  MapPinHouse,
  Phone,
  Pill,
  Receipt,
  Shield,
  Siren,
  TestTube,
} from "lucide-react";
import { Button } from "@/components/ui/button";
import Link from "next/link";
import { useQuery } from "@tanstack/react-query";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";

export default function Page() {
  const getCurrentUserInformationQuery = useQuery(
    createGetCurrentUserInformationQueryOptions(),
  );

  return (
    <div className="grid grid-cols-12 gap-4">
      <div className="col-span-full lg:col-span-6 grid gap-4">
        <Card>
          <CardHeader>
            <CardDescription className="flex items-center">
              <Avatar>
                <AvatarImage src="https://github.com/shadcn.png" />
                <AvatarFallback>CN</AvatarFallback>
              </Avatar>
              <span className="ml-2 text-xl font-bold text-foreground">
                {getCurrentUserInformationQuery.data?.name}
              </span>
            </CardDescription>
          </CardHeader>
          <CardContent>
            <CardTitle className="text-xl">Contact Details</CardTitle>
            <ul className="mt-2">
              {[
                {
                  type: "email",
                  value: getCurrentUserInformationQuery.data?.email,
                },
                {
                  type: "phone",
                  value: "+84 123 456 789",
                },
                {
                  type: "address",
                  value: "123 Main St, HCMC, Vietnam",
                },
              ]
                .map((item) => ({
                  icon:
                    item.type === "email"
                      ? Mail
                      : item.type === "phone"
                        ? Phone
                        : item.type === "address"
                          ? MapPinHouse
                          : AtSign,
                  ...item,
                }))
                .map((item) => (
                  <li key={item.type} className="flex space-x-2">
                    <span className="w-6 h-6 flex items-center justify-center">
                      <item.icon className="size-4 text-primary" />
                    </span>
                    <span className="break-words w-full">{item.value}</span>
                  </li>
                ))}
            </ul>
          </CardContent>
          <CardFooter className="justify-end">
            <Button asChild size="sm" variant="default">
              <Link href="/profile/edit">Edit Profile</Link>
            </Button>
          </CardFooter>
        </Card>
        <Card>
          <CardHeader>
            <CardTitle className="text-xl">Latest Test Results</CardTitle>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Name</TableHead>
                  <TableHead>Date</TableHead>
                  <TableHead>Status</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                <TableRow>
                  <TableCell>COVID-19</TableCell>
                  <TableCell>2021-09-01</TableCell>
                  <TableCell>
                    <Badge variant="destructive">Negative</Badge>
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell>COVID-19</TableCell>
                  <TableCell>2021-09-01</TableCell>
                  <TableCell>
                    <Badge variant="outline">Pending</Badge>
                  </TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </CardContent>
          <CardFooter className="justify-between">
            <Button asChild size="sm" variant="ghost">
              <Link href="/profile/tests/new">Add New Test</Link>
            </Button>
            <Button asChild size="sm" variant="default">
              <Link href="/profile/tests">View All Tests</Link>
            </Button>
          </CardFooter>
        </Card>
      </div>
      <div className="col-span-full lg:col-span-6 grid gap-4">
        <Card className="col-span-full">
          <CardHeader>
            <CardTitle className="text-xl">Overview</CardTitle>
          </CardHeader>
          <CardContent>
            <ul className="grid grid-cols-3 gap-4">
              {[
                {
                  label: "Gender",
                  value: "Female",
                },
                {
                  label: "Date of Birth",
                  value: "1990-01-01",
                },
                {
                  label: "Next of Kin",
                  value: "Daniel Nguyen",
                },
                {
                  label: "Previous Visit",
                  value: "2021-09-01",
                },
                {
                  label: "Next Visit",
                  value: "2021-09-01",
                },
                {
                  label: "Allergies",
                  value: "Peanuts, Shellfish",
                },
              ].map((item) => (
                <li key={item.label} className="grid">
                  <span className="text-sm text-muted-foreground">
                    {item.label}
                  </span>
                  <span className="text-lg font-semibold">{item.value}</span>
                </li>
              ))}
            </ul>
          </CardContent>
        </Card>
        <Card className="col-span-full">
          <CardHeader>
            <CardTitle className="text-xl">Features</CardTitle>
          </CardHeader>
          <CardContent>
            <ul className="grid grid-cols-2 gap-4">
              {[
                {
                  label: "Appointment",
                  link: "/appointments",
                  icon: Calendar,
                },
                {
                  label: "Tests",
                  link: "/profile/tests",
                  icon: TestTube,
                },
                {
                  label: "Prescriptions",
                  link: "/profile/prescriptions",
                  icon: Pill,
                },
                {
                  label: "Invoices",
                  link: "/profile/invoices",
                  icon: Receipt,
                },
                {
                  label: "Insurance",
                  link: "/profile/insurance",
                  icon: Shield,
                },
                {
                  label: "Emergency",
                  link: "/profile/emergency",
                  icon: Siren,
                },
              ].map((item) => (
                <li key={item.label}>
                  <Button
                    size={"lg"}
                    className="h-16 w-full justify-start"
                    variant={"outline"}
                    asChild
                  >
                    <Link href={item.link || "#"}>
                      <div className="w-10 h-10 flex items-center justify-center bg-muted rounded-full">
                        <item.icon className="size-6 text-primary" />
                      </div>
                      <span className="ml-4 text-lg font-semibold">
                        {item.label}
                      </span>
                    </Link>
                  </Button>
                </li>
              ))}
            </ul>
          </CardContent>
          <CardFooter className="justify-end"></CardFooter>
        </Card>
      </div>
    </div>
  );
}
