"use client";

import { useState } from "react";
import AggregatedSideEffects from "./_components/aggregated-side-effects";
import Header from "./_components/header";
import MedicalStatus from "./_components/medical-status";
import PrescriptionsList from "./_components/prescriptions-list";
import SideEffectsReporting from "./_components/side-effects-reporting";

export default function HealthManagementPage() {
  const [dateRange, setDateRange] = useState({ start: "", end: "" });
  const [selectedMedication, setSelectedMedication] = useState("");

  return (
    <div className="container mx-auto px-4 py-8">
      <Header />
      <div className="mt-8">
        <MedicalStatus />
      </div>
      <div className="mt-12">
        <PrescriptionsList />
      </div>
      <div className="mt-12">
        <SideEffectsReporting />
      </div>
      <div className="mt-12">
        <AggregatedSideEffects
          dateRange={dateRange}
          setDateRange={setDateRange}
          selectedMedication={selectedMedication}
          setSelectedMedication={setSelectedMedication}
        />
      </div>
    </div>
  );
}
