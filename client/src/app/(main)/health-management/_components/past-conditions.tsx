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

import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
import { useQuery, useSuspenseQuery } from "@tanstack/react-query";
import { createListPastConditionsQueryOptions} from "@/lib/apis/list-past-conditions.api";
import { useSearchParams } from 'next/navigation'
export default function PastConditions(){

    const searchParams = useSearchParams()
    const userIdSearch = searchParams.get('as')

    const currentUserInformationQuery = useSuspenseQuery(
        createGetCurrentUserInformationQueryOptions(),
      );

    const listPastConditionsQuery = useQuery(
        createListPastConditionsQueryOptions({
            userId: userIdSearch ||  currentUserInformationQuery.data?.userId,
        }),
      );
    
    const pastConditions = listPastConditionsQuery.data?.content ?? [];

  

  return (
    <Card>
      <CardHeader>
        <CardTitle>My Past Conditions</CardTitle>
      </CardHeader>
      <CardContent>

        
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>Condition</TableHead>
              <TableHead>Description</TableHead>
              <TableHead>Date Diagnosed</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {pastConditions.map((pastCondition) => (
              <TableRow >
                <TableCell>{pastCondition.condition}</TableCell>
                <TableCell>{pastCondition.description}</TableCell>
                <TableCell>{pastCondition.dateDiagnosed}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </CardContent>
    </Card>
  );
}
