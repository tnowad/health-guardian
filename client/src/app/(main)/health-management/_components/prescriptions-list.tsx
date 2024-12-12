"use client";

import { useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
import { useQuery, useSuspenseQuery } from "@tanstack/react-query";
import { createListPrescriptionsQueryOptions } from "@/lib/apis/list-prescriptions.api";
import { useSearchParams } from 'next/navigation'


export default function PrescriptionsList() {

  const searchParams = useSearchParams()
  const userIdSearch = searchParams.get('as')


  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions(),
  );

  const listprescriptionsQuery = useQuery( 
    createListPrescriptionsQueryOptions({
       userId: userIdSearch ||  currentUserInformationQuery.data?.userId,
    }),
  );

  const prescriptions = listprescriptionsQuery.data?.content ?? [];


  return (
    <Card>
      <CardHeader>
        <CardTitle>Prescriptions</CardTitle>
      </CardHeader>
      <CardContent>
        <ul className="space-y-4">
          {prescriptions.map((prescription) => (
            <li key={prescription.id} className="p-4 bg-gray-100 rounded-lg">
              <div className="flex justify-between items-center">
                <div>
                  <p className="font-semibold">{prescription.issuedBy}</p>
                  <p>
                    {prescription.issuedDate}, {prescription.status}
                  </p>
                  <p className="text-sm text-gray-600">
                    {prescription.validUntil}
                  </p>
                </div>
              
              </div>
              
            </li>
          ))}
        </ul>
      </CardContent>
    </Card>
  );
}
