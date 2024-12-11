"use client";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import Link from "next/link";
import { useQuery, useSuspenseQuery } from "@tanstack/react-query";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
import { createListDiagnosticReportsQueryOptions } from "@/lib/apis/list-diagnostic-report.api"; // API mới cho báo cáo chẩn đoán

export default function DiagnosticReportScreen() {
  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions()
  );

  const listDiagnosticReportsQuery = useQuery(
    createListDiagnosticReportsQueryOptions({
      userId: currentUserInformationQuery.data?.userId,
    })
  );

  if (listDiagnosticReportsQuery.error) {
    return (
      <Card>
        <CardHeader>
          <CardTitle>Error</CardTitle>
        </CardHeader>
        <CardContent>
          <p className="text-red-500">Failed to load diagnostic reports. Please try again later.</p>
        </CardContent>
      </Card>
    );
  }

  const diagnosticReports = listDiagnosticReportsQuery.data?.content ?? [];

  return (
    <Card>
      <CardHeader>
        <CardTitle>My Diagnostic Reports</CardTitle>
      </CardHeader>
      <CardContent>
        <Button asChild>
          <Link href="/diagnostic-reports/create">Create new diagnostic report</Link>
        </Button>

        <table className="w-full table-auto mt-4 border-collapse border border-gray-300">
          <thead>
            <tr className="bg-gray-100">
              <th className="border border-gray-300 p-2">ID</th>
              <th className="border border-gray-300 p-2">Report Type</th>
              <th className="border border-gray-300 p-2">Report Date</th>
              <th className="border border-gray-300 p-2">Summary</th>
              <th className="border border-gray-300 p-2">Notes</th>
              <th className="border border-gray-300 p-2">Action</th>
            </tr>
          </thead>
          <tbody>
            {diagnosticReports.length > 0 ? (
              diagnosticReports.map((report) => (
                <tr key={report.id} className="hover:bg-gray-50">
                  <td className="border border-gray-300 p-2">{report.id}</td>
                  <td className="border border-gray-300 p-2">{report.reportType ?? "-"}</td>
                  <td className="border border-gray-300 p-2">{report.reportDate}</td>
                  <td className="border border-gray-300 p-2">{report.summary ?? "-"}</td>
                  <td className="border border-gray-300 p-2">{report.notes ?? "-"}</td>
                  <td className="border border-gray-300 p-2 text-center">
                    {currentUserInformationQuery.data.userId === report.userId ? (
                      <Button asChild>
                        <Link
                          href={`/diagnostic-reports/edit/${report.id}`}
                          className="text-blue-500 hover:underline"
                        >
                          Edit
                        </Link>
                      </Button>
                    ) : (
                      "-"
                    )}
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td className="border border-gray-300 p-2" colSpan={6}>
                  No diagnostic reports found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </CardContent>
    </Card>
  );
}
