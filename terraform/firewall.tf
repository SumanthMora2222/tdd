resource "google_compute_firewall" "sumanth_firewall" {
  name = "sumanth-firewall"
  network = google_compute_network.sumanth_vpc_network.self_link
  target_tags = ["allow-ssh-http-https"]
  allow {
    protocol = "tcp"
    ports = [22, 443, 80]
  }
}