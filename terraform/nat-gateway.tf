resource "google_compute_router" "sumanth_router" {
  name = "sumanth-router"
  network = google_compute_network.sumanth_vpc_network.self_link
}

resource "google_compute_router_nat" "sumanth_router_nat" {
  name = "sumanth-router-nat"
  router = google_compute_router.sumanth_router.name
  nat_ip_allocate_option = "AUTO_ONLY"
  source_subnetwork_ip_ranges_to_nat = "ALL_SUBNETWORKS_ALL_IP_RANGES"
}
