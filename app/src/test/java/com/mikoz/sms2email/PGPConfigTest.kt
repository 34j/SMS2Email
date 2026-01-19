package io.github.sms2email.sms2email

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests for PGP configuration
 */
class PGPConfigTest {
  @Test
  fun defaultConfig_pgpDisabled() {
    val config = PreferencesManager.defaultConfig
    assertFalse("PGP should be disabled by default", config.pgpEnabled)
    assertTrue("PGP key IDs should be empty by default", config.pgpKeyIds.isEmpty())
  }

  @Test
  fun smtpConfig_withPGPEnabled() {
    val config = SmtpConfig(
        smtpHost = "smtp.test.com",
        smtpPort = 587,
        pgpEnabled = true,
        pgpKeyIds = listOf(123456789L, 987654321L)
    )
    assertTrue("PGP should be enabled", config.pgpEnabled)
    assertEquals("Should have 2 key IDs", 2, config.pgpKeyIds.size)
    assertEquals("First key ID should match", 123456789L, config.pgpKeyIds[0])
    assertEquals("Second key ID should match", 987654321L, config.pgpKeyIds[1])
  }

  @Test
  fun smtpConfig_withPGPDisabled() {
    val config = SmtpConfig(
        smtpHost = "smtp.test.com",
        smtpPort = 587,
        pgpEnabled = false,
        pgpKeyIds = emptyList()
    )
    assertFalse("PGP should be disabled", config.pgpEnabled)
    assertTrue("PGP key IDs should be empty", config.pgpKeyIds.isEmpty())
  }
}
