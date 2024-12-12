"use client";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import Link from "next/link";
import { useQuery, useSuspenseQuery } from "@tanstack/react-query";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
import { createListDiagnosticResultsQueryOptions } from "@/lib/apis/list-diagnostic-results.api"; // API mới cho báo cáo chẩn đoán

export default function DiagnosticResultScreen() {
  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions()
  );

  const listDiagnosticReportsQuery = useQuery(
    createListDiagnosticResultsQueryOptions({
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
          <p className="text-red-500">
            Failed to load diagnostic reports. Please try again later.
          </p>
        </CardContent>
      </Card>
    );
  }

  const diagnosticReports = listDiagnosticReportsQuery.data?.content ?? [];

  return (
    <Card>
      <CardHeader>
        <CardTitle>My Diagnostic Results</CardTitle>
      </CardHeader>
      <CardContent>
        <Button asChild>
          <Link href="/diagnostic-reports/create">
            Create new diagnostic results
          </Link>
        </Button>

        <table className="w-full table-auto mt-4 border-collapse border border-gray-300">
          <thead>
            <tr className="bg-gray-100">
              <th className="border border-gray-300 p-2">ID</th>
              <th className="border border-gray-300 p-2">Test Name</th>
              <th className="border border-gray-300 p-2">Report Date</th>
              <th className="border border-gray-300 p-2">Result Value</th>
              <th className="border border-gray-300 p-2">Notes</th>
              <th className="border border-gray-300 p-2">Action</th>
            </tr>
          </thead>
          <tbody>
            {diagnosticReports.length > 0 ? (
              diagnosticReports.map((report) => (
                <tr key={report.id} className="hover:bg-gray-50">
                  <td className="border border-gray-300 p-2">{report.id}</td>
                  <td className="border border-gray-300 p-2">
                    {report.testName}
                  </td>
                  <td className="border border-gray-300 p-2">
                    {report.resultDate}
                  </td>
                  <td className="border border-gray-300 p-2">
                    {report.resultValue}
                  </td>
                  <td className="border border-gray-300 p-2">
                    {report.notes ?? "-"}
                  </td>
                  <td className="border border-gray-300 p-2 text-center">
                    {currentUserInformationQuery.data.userId ===
                    report.userId ? (
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
