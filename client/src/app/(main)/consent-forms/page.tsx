
'use client'

import { useState } from 'react'
import ConsentList from './_components/consent-list'
import EmptyState from './_components/empty-state'
import Header from './_components/header'

interface ConsentForm {
  id: string
  name: string
  date: string
  granted: boolean
}

const initialConsentForms: ConsentForm[] = [
  { id: '1', name: 'General Medical Consent', date: '2023-06-01', granted: true },
  { id: '2', name: 'HIPAA Privacy Agreement', date: '2023-06-02', granted: false },
  { id: '3', name: 'Telemedicine Consent', date: '2023-06-03', granted: true },
]

export default function ConsentFormsPage() {
  const [consentForms, setConsentForms] = useState<ConsentForm[]>(initialConsentForms)

  const toggleConsent = (id: string) => {
    setConsentForms(consentForms.map(form =>
      form.id === id ? { ...form, granted: !form.granted } : form
    ))
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <Header />
      {consentForms.length > 0 ? (
        <ConsentList consentForms={consentForms} toggleConsent={toggleConsent} />
      ) : (
        <EmptyState />
      )}
    </div>
  )
}

