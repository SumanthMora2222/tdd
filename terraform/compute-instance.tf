resource "google_compute_address" "sumanth_static" {
  name = "sumanth-ipv4-address"
}

resource "google_service_account" "sumanth_service_account" {
  account_id   = "sumanth-service-account-id"
  display_name = "Sumanth Service Account"
}

resource "google_compute_instance" "sumanth_instance" {
  name = "sumanth-instance"
  machine_type = "f1-micro"
  zone = "europe-west3-a"
  boot_disk {
    initialize_params {
      image = "debian-cloud/debian-9"
    }
  }
  network_interface {
    subnetwork = google_compute_subnetwork.sumanth_network-with-private-secondary-ip-ranges.self_link
    access_config {
      nat_ip = google_compute_address.sumanth_static.address
    }
  }
  service_account {
    email  = google_service_account.sumanth_service_account.email
    scopes = ["cloud-platform"]
  }
  allow_stopping_for_update = true
}