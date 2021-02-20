resource "google_compute_network" "sumanth_vpc_network" {
  name                    = "sumanth-vpc-network"
  auto_create_subnetworks = false
}

resource "google_compute_subnetwork" "sumanth_network-with-private-secondary-ip-ranges" {
  name          = "sumanth-subnetwork"
  ip_cidr_range = "10.2.0.0/16"
  region        = "europe-west3"
  network       = google_compute_network.sumanth_vpc_network.self_link
  secondary_ip_range {
    range_name    = "sumanth-secondary-range-update"
    ip_cidr_range = "192.168.10.0/24"
  }
}



