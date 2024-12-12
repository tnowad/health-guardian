"use client";

import { useState } from "react";
import AggregatedSideEffects from "./_components/aggregated-side-effects";
import Header from "./_components/header";
import MedicalStatus from "./_components/medical-status";
import PrescriptionsList from "./_components/prescriptions-list";
import SideEffectsReporting from "./_components/side-effects-reporting";
import PastConditionScreen from "../past-conditions/page";
import PastConditions from "./_components/past-conditions";
import SurgeryScreen from "./_components/surgeries";
import VaccinationScreen from "./_components/vaccinations";
import PhysicianNotesScreen from "./_components/physician-notes";

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
        <PastConditions />
      </div>
      <div className="mt-12">
        <SurgeryScreen />
      </div>
      <div className="mt-12">
        <VaccinationScreen />
    
      </div>
      <div className="mt-12">
        <PhysicianNotesScreen />
        </div>
    </div>
  );
}
