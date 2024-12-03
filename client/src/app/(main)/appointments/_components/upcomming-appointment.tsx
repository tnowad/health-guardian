import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

interface Appointment {
  id: string;
  date: string;
  doctor: string;
  reason: string;
  status: string;
}

const mockUpcomingAppointments: Appointment[] = [
  {
    id: "1",
    date: "2023-06-15 10:00 AM",
    doctor: "Dr. Smith",
    reason: "Annual Checkup",
    status: "Confirmed",
  },
  {
    id: "2",
    date: "2023-06-20 2:30 PM",
    doctor: "Dr. Johnson",
    reason: "Follow-up",
    status: "Pending",
  },
];

export default function UpcomingAppointments() {
  return (
    <Card>
      <CardHeader>
        <CardTitle>Upcoming Appointments</CardTitle>
      </CardHeader>
      <CardContent>
        {mockUpcomingAppointments.length > 0 ? (
          <ul className="space-y-4">
            {mockUpcomingAppointments.map((appointment) => (
              <li
                key={appointment.id}
                className="flex justify-between items-center p-4 bg-gray-100 rounded-lg"
              >
                <div>
                  <p className="font-semibold">{appointment.date}</p>
                  <p>{appointment.doctor}</p>
                  <p className="text-sm text-gray-600">{appointment.reason}</p>
                  <p className="text-sm font-medium">{appointment.status}</p>
                </div>
                <div className="space-x-2">
                  <Button variant="outline" size="sm">
                    Reschedule
                  </Button>
                  <Button variant="destructive" size="sm">
                    Cancel
                  </Button>
                </div>
              </li>
            ))}
          </ul>
        ) : (
          <p className="text-center text-gray-500">No upcoming appointments</p>
        )}
      </CardContent>
    </Card>
  );
}
