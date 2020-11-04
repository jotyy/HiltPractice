package top.jotyy.hiltpractice

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import top.jotyy.hiltpractice.data.State
import top.jotyy.hiltpractice.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)
        observeData()
    }

    private fun observeData() {
        viewModel.state.observe(this, { state ->
            when (state) {
                State.Loading -> binding.progressView.visibility = View.VISIBLE
                State.Loaded -> binding.progressView.visibility = View.GONE
            }
        })

        viewModel.user.observe(this, { user ->
            binding.run {
                Picasso.get()
                    .load(user.avatarUrl)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(ivAvatar)
                tvName.text = user.name
                tvBio.text = user.bio
                tvFollowers.text = user.followers.toString()
                tvFollowings.text = user.following.toString()
                tvRepositories.text = user.publicRepos.toString()
            }
        })

        viewModel.error.observe(this, { error ->
            Snackbar.make(binding.root, error, Snackbar.LENGTH_SHORT)
                .setAction("RETRY") {
                    viewModel.fetchUser()
                }
        })
    }
}
