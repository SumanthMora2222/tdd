resource "google_compute_firewall" "sumanth_firewall" {
  name = "sumanth-firewall"
  network = google_compute_network.sumanth_vpc_network.self_link
  target_service_accounts = [google_service_account.sumanth_service_account.email]
  allow {
    protocol = "tcp"
    ports = [22]
  }
}