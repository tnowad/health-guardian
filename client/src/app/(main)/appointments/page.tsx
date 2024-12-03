"use client";

import { useState } from "react";
import AppointmentBookingModal from "./_components/appointment-booking-modal";
import Header from "./_components/header";
import PastAppointments from "./_components/past-appointments";
import UpcomingAppointments from "./_components/upcomming-appointment";

export default function AppointmentsPage() {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => setIsModalOpen(true);
  const closeModal = () => setIsModalOpen(false);

  return (
    <div className="container mx-auto px-4 py-8">
      <Header onBookAppointment={openModal} />
      <div className="mt-8">
        <UpcomingAppointments />
      </div>
      <div className="mt-12">
        <PastAppointments />
      </div>
      <AppointmentBookingModal isOpen={isModalOpen} onClose={closeModal} />
    </div>
  );
}
