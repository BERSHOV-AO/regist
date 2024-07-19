package ru.nak.ied.regist.entities

data class AgvDiagnostic(
    val id: Int?,
    val serialNumber: String,
    val diagnosticShassi: Boolean,
    val diagnosticsBattery: Boolean,
    val diagnosticSensoryPanel: Boolean,
    val diagnosticsPin: Boolean,
    val diagnosticLaserScanner: Boolean,
    val diagnosticRfidReader: Boolean,
    val diagnosticSoundSignal: Boolean,
    val diagnosticLightIndication: Boolean,
    val timeLastDiagnostics: String
    )
