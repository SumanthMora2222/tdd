resource "google_compute_region_instance_group_manager" "sumanth_instance_group_manager" {
  name = "sumanth-tdd-instance-group-manager"

  base_instance_name = "sumanth-tdd"
  region = "europe-west3"

  version {
    instance_template  = google_compute_instance_template.sumanth_instance_template.id
  }

  target_size  = 2
}
