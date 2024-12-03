import { Card, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { CheckCircle, XCircle } from "lucide-react";

interface ConsentForm {
  id: string;
  name: string;
  date: string;
  granted: boolean;
}

interface ConsentListProps {
  consentForms: ConsentForm[];
  toggleConsent: (id: string) => void;
}

export default function ConsentList({
  consentForms,
  toggleConsent,
}: ConsentListProps) {
  return (
    <div className="space-y-4">
      {consentForms.map((form) => (
        <Card key={form.id}>
          <CardContent className="flex items-center justify-between p-4">
            <div className="flex items-center space-x-4">
              {form.granted ? (
                <CheckCircle className="h-6 w-6 text-green-500" />
              ) : (
                <XCircle className="h-6 w-6 text-red-500" />
              )}
              <div>
                <h2 className="text-lg font-semibold">{form.name}</h2>
                <p className="text-sm text-gray-500">
                  Date: {new Date(form.date).toLocaleDateString()}
                </p>
              </div>
            </div>
            <div className="flex items-center space-x-2">
              <p
                className={`text-sm font-medium ${form.granted ? "text-green-500" : "text-red-500"}`}
              >
                {form.granted ? "Granted" : "Revoked"}
              </p>
              <Button
                variant={form.granted ? "destructive" : "default"}
                size="sm"
                onClick={() => toggleConsent(form.id)}
              >
                {form.granted ? "Revoke" : "Grant"}
              </Button>
            </div>
          </CardContent>
        </Card>
      ))}
    </div>
  );
}
