"use client";

import { useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Textarea } from "@/components/ui/textarea";
import { Button } from "@/components/ui/button";

const sideEffects = [
  "Nausea",
  "Headache",
  "Dizziness",
  "Fatigue",
  "Rash",
  "Other",
];

const severityLevels = ["Mild", "Moderate", "Severe"];

export default function SideEffectsReporting() {
  const [sideEffect, setSideEffect] = useState("");
  const [severity, setSeverity] = useState("");
  const [notes, setNotes] = useState("");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // Handle side effect report submission logic here
    console.log("Submitting side effect report:", {
      sideEffect,
      severity,
      notes,
    });
    // Reset form
    setSideEffect("");
    setSeverity("");
    setNotes("");
  };

  return (
    <Card>
      <CardHeader>
        <CardTitle>Report Side Effects</CardTitle>
      </CardHeader>
      <CardContent>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label
              htmlFor="side-effect"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              Side Effect
            </label>
            <Select value={sideEffect} onValueChange={setSideEffect} required>
              <SelectTrigger id="side-effect">
                <SelectValue placeholder="Select a side effect" />
              </SelectTrigger>
              <SelectContent>
                {sideEffects.map((effect) => (
                  <SelectItem key={effect} value={effect.toLowerCase()}>
                    {effect}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
          <div>
            <label
              htmlFor="severity"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              Severity
            </label>
            <Select value={severity} onValueChange={setSeverity} required>
              <SelectTrigger id="severity">
                <SelectValue placeholder="Select severity level" />
              </SelectTrigger>
              <SelectContent>
                {severityLevels.map((level) => (
                  <SelectItem key={level} value={level.toLowerCase()}>
                    {level}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
          <div>
            <label
              htmlFor="notes"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              Notes
            </label>
            <Textarea
              id="notes"
              value={notes}
              onChange={(e) => setNotes(e.target.value)}
              placeholder="Provide any additional details about the side effect"
              rows={4}
            />
          </div>
          <Button type="submit">Submit Report</Button>
        </form>
      </CardContent>
    </Card>
  );
}
