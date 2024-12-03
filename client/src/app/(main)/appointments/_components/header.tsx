import { Button } from "@/components/ui/button";

interface HeaderProps {
  onBookAppointment: () => void;
}

export default function Header({ onBookAppointment }: HeaderProps) {
  return (
    <div className="flex justify-between items-center">
      <h1 className="text-3xl font-bold">Appointments</h1>
      <Button onClick={onBookAppointment}>Book Appointment</Button>
    </div>
  );
}
